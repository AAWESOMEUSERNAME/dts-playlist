package com.gugu.dts.playlist.core.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FilterTest {

    private val songs = listOf<Song>(
            Song("test1.mp3", "D:/test1.mp3", bpm = 100.0),
            Song("test2.mp3", "D:/test2.mp3", bpm = 110.0),
            Song("test3.mp3", "D:/test3.mp3", bpm = 120.0),
            Song("test4.mp3", "D:/test4.mp3", bpm = 130.0),
            Song("test5.mp3", "D:/test5.mp3", bpm = 140.0),
            Song("test6.mp3", "D:/test6.mp3", bpm = 150.0),
            Song("test7.mp3", "D:/test7.mp3", bpm = 160.0),
            Song("test8.mp3", "D:/test8.mp3", bpm = 170.0),
            Song("test9.mp3", "D:/test9.mp3", bpm = 180.0)
    )

    @Test
    fun filter() {
        assertEquals(2, Filter(100.0, 115.0).filter(songs).size)
        assertEquals(2, Filter(100.0, 120.0).filter(songs).size)
        assertEquals(3, Filter(100.0, 130.0).filter(songs).size)
        assertEquals(2, Filter(110.0, 130.0).filter(songs).size)
    }
}