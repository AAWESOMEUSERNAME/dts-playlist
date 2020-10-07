package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.IQuery
import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository

class Query(private val libraryRepo: MusicLibraryRepository) : IQuery {

    override fun fetchLibraryByName(name: String): IMusicLibrary? {
        return libraryRepo.fetchLibraryByName(name)
    }

    override fun listLibrary(): List<IMusicLibrary> {
        return libraryRepo.list()
    }

    override fun fetchLibraryById(id: Long): IMusicLibrary? {
        return libraryRepo.find(id)
    }
}