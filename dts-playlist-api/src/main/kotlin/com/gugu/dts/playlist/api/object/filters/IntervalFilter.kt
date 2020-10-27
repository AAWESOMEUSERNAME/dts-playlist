package com.gugu.dts.playlist.api.`object`.filters

import com.gugu.dts.playlist.api.`object`.IFilter
import com.gugu.dts.playlist.api.constants.PropertyProvider

/**
 * filter that filters data that in [min,max)
 */
interface IntervalFilter:IFilter {
    val min: Double
    val max: Double

    /**
     * function that provide the property to do filter
     */
    val provider: PropertyProvider
}