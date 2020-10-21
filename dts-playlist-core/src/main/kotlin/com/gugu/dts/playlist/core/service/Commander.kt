package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.ICommander
import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
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
        return Rule(dto.filterGroups.map {
            it.first to toCore(it.second)
        }, dto.total)
    }

    override fun getIntervalFilter(min: Double, max: Double, provider: ISong.() -> Double): IntervalFilter {
        return IntervalFilterImpl(min,max,provider)
    }

    private fun toCore(dto: IFilterGroupDTO): IFilterGroup {
        return FilterGroup(dto.filters, dto.logic)
    }
}