package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.`object`.ISong

class Filter(
        override val startBpm: Double,
        override val endBpm: Double
) : IFilter {
    override fun filter(songs: List<ISong>): List<ISong> {
        return songs.filter {
            it.bpm?.compareTo(startBpm) ?: -1 >= 0 && it.bpm?.compareTo(endBpm) ?: 1 < 0
        }
    }
}