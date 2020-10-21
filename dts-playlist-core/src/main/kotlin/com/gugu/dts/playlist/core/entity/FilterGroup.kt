package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.IFilterGroup
import com.gugu.dts.playlist.api.`object`.ISong
import com.gugu.dts.playlist.api.constants.Logic

class FilterGroup(
        override val filters: List<IFilter>,
        override val logic: Boolean
) : IFilterGroup {

    override fun filter(songs: List<ISong>): List<ISong> {
        return filters.map { it.filter(songs) }.reduce { l: List<ISong>, n: List<ISong> ->
            val mutableList = l.toMutableList()
            if (logic == Logic.AND) mutableList.retainAll(n) else mutableList.union(n).toList()
            mutableList.toList()
        }
    }
}