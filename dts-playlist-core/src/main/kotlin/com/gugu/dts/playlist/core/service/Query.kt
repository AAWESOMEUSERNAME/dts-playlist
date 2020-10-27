package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.IQuery
import com.gugu.dts.playlist.api.`object`.IFilterGroup
import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository

class Query(private val libraryRepo: MusicLibraryRepository, private val filterGroupRepo: FilterGroupRepository) : IQuery {

    override fun fetchLibraryByName(name: String): IMusicLibrary? {
        return libraryRepo.fetchLibraryByName(name)
    }

    override fun listLibrary(): List<IMusicLibrary> {
        return libraryRepo.list()
    }

    override fun fetchLibraryById(id: Int): IMusicLibrary? {
        return libraryRepo.find(id)
    }

    override fun listFilterGroups(): List<IFilterGroup> {
        return filterGroupRepo.list()
    }

    override fun fetchFilterGroupById(id: Int): IFilterGroup? {
        return filterGroupRepo.find(id)
    }
}