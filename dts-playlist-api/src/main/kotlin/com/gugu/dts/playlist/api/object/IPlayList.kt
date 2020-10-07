package com.gugu.dts.playlist.api.`object`

import java.io.File

interface IPlayList {
    val songs: MutableList<ISong>
    fun add(song: ISong)
    fun addAll(songs: List<ISong>)
    fun toFile(): File
    fun toFile(path: String): File
}