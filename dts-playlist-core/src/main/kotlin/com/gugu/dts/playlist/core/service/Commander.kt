package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.ICommander
import com.gugu.dts.playlist.api.`object`.*
import com.gugu.dts.playlist.core.entity.Filter
import com.gugu.dts.playlist.core.entity.Rule
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository

class Commander(private val libraryRepo: MusicLibraryRepository) : ICommander {

    override fun importLibray(library: IMusicLibraryDTO): IMusicLibrary {
        return libraryRepo.import(library)
    }

    override fun deleteLibById(currentLibId: Long) {
        return libraryRepo.deleteLibById(currentLibId)
    }

    override fun createRule(ruleDto: IRuleDTO): IRule {
        return Rule(
                ruleDto.filters.map { it.first to toCore(it.second) },
                ruleDto.repeatable,
                ruleDto.totalNeeded
        )
    }

    private fun toCore(dto: IFilterDTO): IFilter {
        return Filter(dto.startBpm, dto.endBpm)
    }
}