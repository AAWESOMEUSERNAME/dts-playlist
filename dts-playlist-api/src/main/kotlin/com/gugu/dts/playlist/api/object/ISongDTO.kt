package com.gugu.dts.playlist.api.`object`

interface ISongDTO {
    val name: String
    val path: String
    val bpm: Double
    val artist: String?
    val trackLength: Int
}