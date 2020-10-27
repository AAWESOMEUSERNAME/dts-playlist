package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.IMusicLibraryDTO
import com.gugu.dts.playlist.api.`object`.IRule
import com.gugu.dts.playlist.api.`object`.IRuleDTO
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider

interface ICommander {
    fun importLibray(library: IMusicLibraryDTO): IMusicLibrary
    fun deleteLibById(currentLibId: Long)
    fun getIntervalFilter(min: Double, max: Double, provider: PropertyProvider): IntervalFilter
    fun createRule(dto: IRuleDTO): IRule
    fun resetLibPlayedTimes(libId: Long)
    fun updateSongPlayedTimes(id: Long, newValue: Int)
    fun updateSongPlayedTimesByOne(ids: LongArray)
}