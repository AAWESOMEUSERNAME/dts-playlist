package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.IMusicLibrary
import com.gugu.dts.playlist.api.`object`.ISong
import java.util.*

class MusicLibrary(
        override val name: String,
        override val path: String,
        override val songs: List<ISong>,
        override val id: Int? = null,
        override val createAt: Date
) : IMusicLibrary