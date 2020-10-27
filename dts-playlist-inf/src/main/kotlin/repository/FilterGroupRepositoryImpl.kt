package repository

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonReader
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.IFilterGroup
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.inf.entity.FilterGroup
import com.gugu.dts.playlist.inf.mapper.FilterGroupMapper
import java.io.StringReader

class FilterGroupRepositoryImpl(private val filterGroupMapper: FilterGroupMapper) : FilterGroupRepository {
    override fun list(): List<IFilterGroup> {
        return filterGroupMapper.list().map {
            toModule(it)
        }
    }

    override fun find(id: Int): IFilterGroup? {
        val entity = filterGroupMapper.selectByPrimaryKey(id)
        return if (entity == null) null else toModule(entity)
    }

    private fun toModule(entity: FilterGroup): IFilterGroup {
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
    override fun canConvert(cls: Class<*>) = cls is IFilter

    override fun fromJson(jv: JsonValue): Any? {
        return when (val type = jv.objString(TYPE)) {
            IntervalFilter::class.simpleName -> IntervalFilterImpl(jv.objString(INTERVAL_MAX).toDouble(), jv.objString(INTERVAL_MIN).toDouble(), PropertyProvider.valueOf(jv.objString(INTERVAL_PROVIDER)))
            else -> throw RuntimeException("invalid type: $type")
        }
    }

    override fun toJson(value: Any): String {
        return when (value) {
            is IntervalFilter -> """{"$TYPE":"${value::class.simpleName}",$INTERVAL_MAX":"${value.max}","$INTERVAL_MIN":"${value.min},"$INTERVAL_PROVIDER":"${value.provider.name}"}"""
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