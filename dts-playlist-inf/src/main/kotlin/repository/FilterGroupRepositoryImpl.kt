package repository

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonReader
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.IFilterGroupDTO
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.inf.entity.FilterGroup
import com.gugu.dts.playlist.inf.mapper.FilterGroupMapper
import java.io.StringReader

class FilterGroupRepositoryImpl(private val filterGroupMapper: FilterGroupMapper) : FilterGroupRepository {
    override fun list(): List<com.gugu.dts.playlist.core.entity.FilterGroup> {
        return filterGroupMapper.list().map {
            toModule(it)
        }
    }

    override fun find(id: Int): com.gugu.dts.playlist.core.entity.FilterGroup? {
        val entity = filterGroupMapper.selectByPrimaryKey(id)
        return if (entity == null) null else toModule(entity)
    }

    override fun updateFilterGroup(group: com.gugu.dts.playlist.core.entity.FilterGroup) {
        filterGroupMapper.updateByPrimaryKeySelective(toEntity(group))
    }

    override fun deleteFilterGroupById(id: Int) {
        filterGroupMapper.deleteByPrimaryKey(id)
    }

    override fun insert(dto: IFilterGroupDTO) {
        filterGroupMapper.insert(toEntity(dto))
    }

    private fun toEntity(dto: IFilterGroupDTO): FilterGroup {
        val filterGroup = FilterGroup()
        filterGroup.name = dto.name
        filterGroup.sum = dto.sum
        filterGroup.logic = if (dto.logic) 1 else 0
        filterGroup.conditionDescription = dto.description
        filterGroup.conditionJson = toArrayJson(dto.filters)
        return filterGroup
    }

    private fun toModule(entity: FilterGroup): com.gugu.dts.playlist.core.entity.FilterGroup {
        return com.gugu.dts.playlist.core.entity.FilterGroup(
                entity.id,
                toIFilterList(entity.conditionJson),
                entity.logic == 1,
                entity.conditionDescription,
                entity.name,
                entity.sum
        )
    }

    private fun toEntity(module: com.gugu.dts.playlist.core.entity.FilterGroup): FilterGroup {
        val filterGroup = FilterGroup()
        filterGroup.id = module.id
        filterGroup.name = module.name
        filterGroup.sum = module.sum
        filterGroup.logic = if (module.logic) 1 else 0
        filterGroup.conditionDescription = module.description
        filterGroup.conditionJson = toArrayJson(module.filters)
        return filterGroup
    }

    private fun toArrayJson(filters: List<IFilter>): String {
        return filters.mapNotNull {
            when (it) {
                is IntervalFilter -> Klaxon().converter(IFilterConverter.instant).toJsonString(it)
                else -> null
            }
        }.joinToString(separator = ",", prefix = "[", postfix = "]")
    }

    private fun toIFilterList(s: String): List<IFilter> {
        val filters = arrayListOf<IFilter>()
        JsonReader(StringReader(s)).use {
            it.beginArray {
                while (it.hasNext()) {
                    val filter = Klaxon().converter(IFilterConverter.instant).parse<IFilter>(it)
                    if (filter != null) filters.add(filter)
                }
            }
        }
        return filters
    }

}

class IFilterConverter : Converter {
    override fun canConvert(cls: Class<*>): Boolean {
        return IFilter::class.java.isAssignableFrom(cls)
    }

    override fun fromJson(jv: JsonValue): Any? {
        return when (val type = jv.objString(TYPE)) {
            IntervalFilterImpl::class.simpleName -> IntervalFilterImpl(max = jv.objString(INTERVAL_MAX).toDouble(), min = jv.objString(INTERVAL_MIN).toDouble(), provider = PropertyProvider.valueOf(jv.objString(INTERVAL_PROVIDER)))
            else -> throw RuntimeException("invalid type: $type")
        }
    }

    override fun toJson(value: Any): String {
        return when (value) {
            is IntervalFilter -> """{"$TYPE":"${value::class.simpleName}","$INTERVAL_MAX":"${value.max}","$INTERVAL_MIN":"${value.min}","$INTERVAL_PROVIDER":"${value.provider.name}"}"""
            else -> throw RuntimeException("invalid object: $value")
        }
    }

    companion object {
        val instant = IFilterConverter()
        const val TYPE = "type"

        const val INTERVAL_MAX = "max"
        const val INTERVAL_MIN = "min"
        const val INTERVAL_PROVIDER = "provider"
    }
}