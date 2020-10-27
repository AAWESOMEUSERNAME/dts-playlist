package com.gugu.dts.playlist.core.entity.filters

import com.gugu.dts.playlist.api.`object`.ISong
import com.gugu.dts.playlist.api.`object`.filters.IntervalFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider

class IntervalFilterImpl(override val min: Double, override val max: Double, override val provider: PropertyProvider) : IntervalFilter {
    override fun filter(songs: List<ISong>): List<ISong> {
        return songs.filter { min <= it.(provider.function)() && max > it.(provider.function)() }
    }
}