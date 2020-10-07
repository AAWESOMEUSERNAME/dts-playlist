package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.IMusicLibrary

interface IQuery {
    fun fetchLibraryByName(name: String): IMusicLibrary?
    fun fetchLibraryById(id: Long): IMusicLibrary?
    fun listLibrary(): List<IMusicLibrary>
}