package com.gugu.dts.playlist.api

import com.gugu.dts.playlist.api.`object`.IFilterGroup
import com.gugu.dts.playlist.api.`object`.IMusicLibrary

interface IQuery {
    fun fetchLibraryByName(name: String): IMusicLibrary?
    fun fetchLibraryById(id: Int): IMusicLibrary?
    fun listLibrary(): List<IMusicLibrary>
    fun listFilterGroups(): List<IFilterGroup>
    fun fetchFilterGroupById(id: Int): IFilterGroup?
}