package com.gugu.dts.playlist.api.`object`

import java.util.*

interface IMusicLibrary {
    val id: Int?
    val name: String
    val path: String
    val songs: List<ISong>
    val createAt: Date
}