package com.gugu.dts.playlist.api.`object`

interface IFilter {
    fun filter(songs: List<ISong>): List<ISong>
}