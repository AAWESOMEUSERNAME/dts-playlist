package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.ICommander
import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.FilterGroup
import com.gugu.dts.playlist.core.entity.Rule
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository

class Commander(private val libraryRepo: MusicLibraryRepository, private val groupRepo: FilterGroupRepository) : ICommander {

    override fun importLibray(library: IMusicLibraryDTO): IMusicLibrary {
        return libraryRepo.import(library)
    }

    override fun deleteLibById(currentLibId: Int) {
        return libraryRepo.deleteLibById(currentLibId)
    }

    override fun createRule(dto: IRuleDTO): IRule {
        return Rule(dto.filterGroups.map { toCore(it) }, dto.total, dto.fairlyMode)
    }

    override fun updateSongPlayedTimesByOne(ids: IntArray) {
        libraryRepo.updateSongPlayedTimesByOne(ids)
    }

    override fun resetLibPlayedTimes(libId: Int) {
        libraryRepo.resetPlayedTimes(libId)
    }

    override fun updateSongPlayedTimes(id: Int, newValue: Int) {
        libraryRepo.updateSongPlayedTimes(id, newValue)
    }

    override fun updateFilterGroup(id: Int, dto: IFilterGroupDTO) {
        groupRepo.updateFilterGroup(toCore(id, dto))
    }

    override fun getIntervalFilter(min: Double, max: Double, provider: PropertyProvider): IntervalFilter {
        return IntervalFilterImpl(min, max, provider)
    }

    private fun toCore(dto: IFilterGroupDTO): FilterGroup {
        return toCore(null, dto)
    }

    override fun deleteFilterGroupById(id: Int) {
        groupRepo.deleteFilterGroupById(id)
    }

    override fun insertFilterGroup(dto: IFilterGroupDTO) {
        groupRepo.insert(dto)
    }

    private fun toCore(id: Int?, dto: IFilterGroupDTO): FilterGroup {
        return FilterGroup(id = id, filters = dto.filters, logic = dto.logic, sum = dto.sum, name = dto.name, description = dto.description)
    }
}