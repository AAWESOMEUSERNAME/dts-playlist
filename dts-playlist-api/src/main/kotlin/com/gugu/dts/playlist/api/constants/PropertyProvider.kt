package com.gugu.dts.playlist.api.constants

import com.gugu.dts.playlist.api.`object`.ISong

enum class PropertyProvider(val function: ISong.() -> Double) {
    BPM(fun ISong.(): Double { return this.bpm ?: 0.0 }),
    LENGTH(fun ISong.(): Double { return this.trackLength.toDouble() })
}