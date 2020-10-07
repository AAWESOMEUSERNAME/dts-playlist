package com.gugu.dts.playlist.core.entity

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class PlayListTest {

    lateinit var playList: PlayList

//    companion object {
//        @BeforeAll
//        @JvmStatic
//        fun beforeAll(){
//            println("beforeAll")
//        }
//    }

    @BeforeEach
    fun init() {
        playList = PlayList(
                mutableListOf(
                        Song(
                                name = "Hop Skip And Jump.mp3",
                                path = "D:\\Data\\music\\09-05. Hop Skip And Jump.mp3"
                        ),
                        Song(
                                name = "Come Fly with Me.mp3",
                                path = "D:\\Data\\CloudMusic\\Frank Sinatra - Come Fly with Me.mp3"
                        )
                )
        )


    }

    @Test
    fun add() {
        playList.add(Song("test2.mp3", "D:/test2.mp3"))
        assertTrue { playList.songs.size == 2 }
    }

    @Test
    fun toFile() {
        val file = playList.toFile()
        assertNotNull(file)
    }

    @Test
    fun toFileWithPath() {
        val file = playList.toFile("D:/test.m3u")
        assertNotNull(file)
        file.delete()
    }
}