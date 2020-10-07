package com.gugu.dts.playlist.api.`object`

interface IFilter {
    val startBpm: Double
    val endBpm: Double
    fun filter(songs: List<ISong>): List<ISong>
}