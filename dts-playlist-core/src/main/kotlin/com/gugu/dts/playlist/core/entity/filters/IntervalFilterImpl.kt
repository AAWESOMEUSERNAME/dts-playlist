package com.gugu.dts.playlist.core.entity.filters

import com.gugu.dts.playlist.api.`object`.ISong
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter

class IntervalFilterImpl(override val min: Double, override val max: Double, override val provider: ISong.() -> Double) : IntervalFilter {
    override fun filter(songs: List<ISong>): List<ISong> {
        return songs.filter { min <= it.provider() && max > it.provider() }
    }
}