package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.IPlayList
import com.gugu.dts.playlist.api.`object`.ISong
import java.io.File

class PlayList() : IPlayList {

    constructor(songs: MutableList<ISong>) : this() {
        this.songs = songs
    }

    override var songs = mutableListOf<ISong>()
    override fun add(song: ISong) {
        songs.add(song)
    }

    override fun addAll(songs: List<ISong>) {
        this.songs.addAll(songs)
    }

    override fun toFile(): File {
        val strList = songs.map { it.path + "\r\n" }
        val file = File.createTempFile("playlist", ".m3u")
        file.outputStream().use { out ->
            strList.forEach {
                out.write(it.toByteArray())
            }
        }
        return file
    }

    override fun toFile(path: String): File {
        val strList = songs.map { it.path + "\r\n" }
        val file = File(path)

        if (file.exists()) {
            file.delete()
        }

        file.outputStream().use { out ->
            strList.forEach {
                out.write(it.toByteArray())
            }
        }
        return file
    }

    override fun toString(): String {
        return "PlayList(songs=$songs)"
    }


}