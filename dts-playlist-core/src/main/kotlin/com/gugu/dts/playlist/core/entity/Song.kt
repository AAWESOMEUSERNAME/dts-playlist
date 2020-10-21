package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.ISong

data class Song(
        override val name: String,
        override val path: String,
        override val usedTimes: Int = 0,
        override val bpm: Double? = null,
        override val artist: String? = null,
        override val trackLength: Int = 0,
        override val id: Int? = null
) : ISong