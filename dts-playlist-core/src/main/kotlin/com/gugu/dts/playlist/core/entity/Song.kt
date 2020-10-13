package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.ISong

class Song(
        override val name: String,
        override val path: String,
        override val usedTimes: Int = 0,
        override val bpm: Double? = null,
        override val trackLength: Int = 0,
        override val id: Int? = null
) : ISong {



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Song) return false

        if (name != other.name) return false
        if (path != other.path) return false
        if (usedTimes != other.usedTimes) return false
        if (bpm != other.bpm) return false
        if (trackLength != other.trackLength) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + usedTimes
        result = 31 * result + (bpm?.hashCode() ?: 0)
        result = 31 * result + trackLength
        result = 31 * result + (id ?: 0)
        return result
    }

    override fun toString(): String {
        return "Song(name='$name', path='$path', usedTimes=$usedTimes, bpm=$bpm, trackLength=$trackLength, id=$id)"
    }
}