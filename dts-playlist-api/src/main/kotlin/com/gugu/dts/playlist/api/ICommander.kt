package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider

interface ICommander {
    fun importLibray(library: IMusicLibraryDTO): IMusicLibrary
    fun deleteLibById(currentLibId: Int)
    fun getIntervalFilter(min: Double, max: Double, provider: PropertyProvider): IntervalFilter
    fun createRule(dto: IRuleDTO): IRule
    fun resetLibPlayedTimes(libId: Int)
    fun updateSongPlayedTimes(id: Int, newValue: Int)
    fun updateSongPlayedTimesByOne(ids: IntArray)
    fun updateFilterGroup(id: Int, dto: IFilterGroupDTO)
    fun deleteFilterGroupById(id: Int)
    fun insertFilterGroup(dto: IFilterGroupDTO)
}