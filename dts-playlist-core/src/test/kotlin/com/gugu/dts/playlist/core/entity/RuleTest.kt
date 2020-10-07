package com.gugu.dts.playlist.core.entity

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class RuleTest {

    private val musicLibrary = MusicLibrary("test", "D:/test", listOf(
            Song("test1.mp3", "D:/test1.mp3", bpm = 100.0),
            Song("test2.mp3", "D:/test2.mp3", bpm = 110.0),
            Song("test3.mp3", "D:/test3.mp3", bpm = 120.0),
            Song("test4.mp3", "D:/test4.mp3", bpm = 130.0),
            Song("test5.mp3", "D:/test5.mp3", bpm = 140.0),
            Song("test6.mp3", "D:/test6.mp3", bpm = 150.0),
            Song("test7.mp3", "D:/test7.mp3", bpm = 160.0),
            Song("test8.mp3", "D:/test8.mp3", bpm = 170.0),
            Song("test9.mp3", "D:/test9.mp3", bpm = 180.0)
    ), createAt = Date())


    @Test
    fun generatePlayList() {
        val totalNeeded = 5
        val filter1Start = 100.0
        val filter1End = 130.0
        val filter1Num = 1

        val filter2Start = 130.0
        val filter2End = 170.0
        val filter2Num = 2


        val filters = listOf(filter1Num to Filter(filter1Start, filter1End), filter2Num to Filter(filter2Start, filter2End))
        val rule = Rule(filters, false, totalNeeded)
        val playList = rule.generatePlayList(musicLibrary)

        val songs = playList.songs
        println(songs.size)
        println(playList)
        assertTrue(songs.size <= totalNeeded)

    }
}