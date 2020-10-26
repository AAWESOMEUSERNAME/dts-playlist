package repository

import com.beust.klaxon.Klaxon
import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.ISong
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import com.gugu.dts.playlist.core.repository.FilterGroupRepository

class FilterGroupRepositoryImpl : FilterGroupRepository {


    private fun toJson(filter: IFilter): String {
        return Klaxon().toJsonString(filter)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val json = Klaxon().toJsonString(IntervalFilterImpl(1.0, 2.0, fun ISong.(): Double {
                return this.bpm ?: 0.0
            }))
            println(json)
            val ob = Klaxon().parse<IntervalFilterImpl>(json)
            print(ob)
        }
    }

}