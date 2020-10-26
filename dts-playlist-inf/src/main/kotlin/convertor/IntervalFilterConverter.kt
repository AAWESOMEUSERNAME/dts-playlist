package convertor

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl

class IntervalFilterConverter : Converter {
    override fun canConvert(cls: Class<*>) = cls is IntervalFilter


    override fun fromJson(jv: JsonValue): Any? {
        val max = jv.objString(max)
        val min = jv.objString(min)
        val provider = jv.objString(provider)
        return IntervalFilterImpl(min, max, PropertyProvider.valueOf(provider))
    }

    override fun toJson(value: Any): String {
        TODO("Not yet implemented")
    }

    companion object {
        val max = "max"
        val min = "min"
        val provider = "provider"
    }
}