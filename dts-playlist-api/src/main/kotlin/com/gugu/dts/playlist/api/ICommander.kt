package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter

interface ICommander {
    fun importLibray(library: IMusicLibraryDTO): IMusicLibrary
    fun deleteLibById(currentLibId: Long)

    fun getIntervalFilter(min: Double, max: Double, provider: ISong.() -> Double): IntervalFilter
    fun createRule(dto: IRuleDTO): IRule
    fun resetLibPlayedTimes(libId: Long)
    fun updateSongPlayedTimes(id: Long, newValue: Int)
    fun updateSongPlayedTimesByOne(ids: LongArray)
}