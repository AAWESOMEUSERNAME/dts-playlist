package com.gugu.dts.playlist.core.entity

import com.gugu.dts.playlist.api.`object`.ISong
import com.gugu.dts.playlist.api.constants.Logic
import com.gugu.dts.playlist.api.constants.PropertyProvider
import com.gugu.dts.playlist.core.entity.filters.IntervalFilterImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class RuleTest {

    val songs = listOf(
            Song("test1.mp3", "D:/test1.mp3", bpm = 100.0, trackLength = 100),
            Song("test2.mp3", "D:/test2.mp3", bpm = 110.0, trackLength = 200),
            Song("test3.mp3", "D:/test3.mp3", bpm = 120.0, trackLength = 100),
            Song("test4.mp3", "D:/test4.mp3", bpm = 130.0, trackLength = 200),
            Song("test5.mp3", "D:/test5.mp3", bpm = 140.0, trackLength = 100),
            Song("test6.mp3", "D:/test6.mp3", bpm = 150.0, trackLength = 200),
            Song("test7.mp3", "D:/test7.mp3", bpm = 160.0, trackLength = 100),
            Song("test8.mp3", "D:/test8.mp3", bpm = 170.0, trackLength = 200),
            Song("test9.mp3", "D:/test9.mp3", bpm = 180.0, trackLength = 100)
    )
    private val musicLibrary = MusicLibrary("test", "D:/test", songs, createAt = Date())


    val bpmProvider = PropertyProvider.BPM
    val lengthProvider = PropertyProvider.LENGTH


    /**
     * total 5
     * 2 group 1: filter 1
     */
    @Test
    fun test1() {
        val total = 5
        val filter = IntervalFilterImpl(100.0, 140.0, bpmProvider)
        val group = FilterGroup(filters = listOf(filter), logic = Logic.AND, sum = 2, description = "")
        val rule = Rule(listOf(group), total)
        val list = rule.generatePlayList(musicLibrary)
        val expact = ResultDTO(songs.filter { it.(bpmProvider.function)() >= 100.0 && it.(bpmProvider.function)() < 140.0 })

        assertTrue(total >= list.songs.size)
        assertTrue(expact.allIn(list.songs))
    }

    /**
     * total 5
     * 2 group 1: filter1 and filter2
     */
    @Test
    fun test2() {
        val total = 5
        val filter1 = IntervalFilterImpl(100.0, 140.0, bpmProvider)
        val filter2 = IntervalFilterImpl(100.0, 150.0, lengthProvider)
        val group = FilterGroup(filters = listOf(filter1, filter2), logic = Logic.AND, sum = 2, description = "")
        val rule = Rule(listOf(group), total)
        val list = rule.generatePlayList(musicLibrary)

        val expact = ResultDTO(songs.filter {
            (it.(bpmProvider.function)() >= 100.0 && it.(bpmProvider.function)() < 140.0) &&
                    (it.(lengthProvider.function)() >= 100.0 && it.(lengthProvider.function)() < 150.0)
        })

        assertTrue(total >= list.songs.size)
        assertTrue(expact.allIn(list.songs))
    }

    /**
     * total 5
     * 2 group 1: filter1 or filter2
     */
    @Test
    fun test3() {
        val total = 5
        val filter1 = IntervalFilterImpl(100.0, 120.0, bpmProvider)
        val filter2 = IntervalFilterImpl(120.0, 150.0, bpmProvider)
        val group = FilterGroup(filters = listOf(filter1, filter2), logic = Logic.OR, sum = 2, description = "")
        val rule = Rule(listOf(group), total)
        val list = rule.generatePlayList(musicLibrary)

        val expact = ResultDTO(songs.filter {
            (it.(bpmProvider.function)() >= 100.0 && it.(bpmProvider.function)() < 140.0)
        })

        assertTrue(total >= list.songs.size)
        assertTrue(expact.allIn(list.songs))
    }

    /**
     * total 5
     * 2 group 1: filter1
     * 2 group 2: filter2
     */
    @Test
    fun test4() {
        val total = 5
        val filter1 = IntervalFilterImpl(100.0, 120.0, bpmProvider)
        val filter2 = IntervalFilterImpl(120.0, 150.0, bpmProvider)
        val group1 = FilterGroup(filters = listOf(filter1), logic = Logic.AND, sum = 2, description = "")
        val group2 = FilterGroup(filters = listOf(filter2), logic = Logic.AND, sum = 2, description = "")
        val rule = Rule(listOf(group1, group2), total)
        val list = rule.generatePlayList(musicLibrary).songs

        assertTrue(list.size <= total)
        for (i in 0 until list.size) {
            val song = list[i]
            when (i) {
                in 0..1 -> assertTrue(song.bpm!! >= 100.0 && song.bpm!! < 120)
                in 2..3 -> assertTrue(song.bpm!! >= 120.0 && song.bpm!! < 150)
                else -> assertTrue(song.bpm!! >= 100.0 && song.bpm!! < 120)
            }
        }
    }
}

class ResultDTO(
        private val songs: List<ISong>
) {
    fun allIn(other: List<ISong>): Boolean {
        println("expected : $songs")
        println("actual : $other")
        return !other.toMutableList().retainAll(songs)
    }
}