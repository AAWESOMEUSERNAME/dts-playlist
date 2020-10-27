package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.ICommander
import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.FilterGroup
import com.gugu.dts.playlist.core.entity.Rule
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository

class Commander(private val libraryRepo: MusicLibraryRepository) : ICommander {

    override fun importLibray(library: IMusicLibraryDTO): IMusicLibrary {
        return libraryRepo.import(library)
    }

    override fun deleteLibById(currentLibId: Long) {
        return libraryRepo.deleteLibById(currentLibId)
    }

    override fun createRule(dto: IRuleDTO): IRule {
        return Rule(dto.filterGroups.map { toCore(it) }, dto.total, dto.fairlyMode)
    }

    override fun updateSongPlayedTimesByOne(ids: LongArray) {
        libraryRepo.updateSongPlayedTimesByOne(ids)
    }

    override fun resetLibPlayedTimes(libId: Long) {
        libraryRepo.resetPlayedTimes(libId)
    }

    override fun updateSongPlayedTimes(id: Long, newValue: Int) {
        libraryRepo.updateSongPlayedTimes(id, newValue)
    }

    override fun getIntervalFilter(min: Double, max: Double, provider: PropertyProvider): IntervalFilter {
        return IntervalFilterImpl(min, max, provider)
    }

    private fun toCore(dto: IFilterGroupDTO): IFilterGroup {
        return FilterGroup(filters = dto.filters, logic = dto.logic, sum = dto.sum, name = dto.name, description = dto.description)
    }
}