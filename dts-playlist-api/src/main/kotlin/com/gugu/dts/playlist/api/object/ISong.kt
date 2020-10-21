package com.gugu.dts.playlist.api.`object`

interface ISong {
    val name: String
    val path: String
    val usedTimes: Int
    val bpm: Double?
    val artist: String?

    /**
     * 播放时长（s）
     */
    val trackLength: Int
    val id: Int?
}
