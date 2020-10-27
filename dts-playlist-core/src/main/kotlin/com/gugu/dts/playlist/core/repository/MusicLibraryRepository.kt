package com.gugu.dts.playlist.core.repository

import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.IMusicLibraryDTO
import com.gugu.dts.playlist.core.entity.MusicLibrary

interface MusicLibraryRepository {
    fun import(library: IMusicLibraryDTO): MusicLibrary
    fun fetchLibraryByName(name: String): MusicLibrary?
    fun list(): List<MusicLibrary>
    fun deleteLibById(currentLibId: Long)
    fun find(id: Int): IMusicLibrary?
    fun resetPlayedTimes(libId: Long)
    fun updateSongPlayedTimes(id: Long, newValue: Int)
    fun updateSongPlayedTimesByOne(ids: LongArray)
}
