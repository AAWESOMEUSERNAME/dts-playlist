package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.api.`object`.IMusicLibraryDTO
import com.gugu.dts.playlist.api.`object`.ISongDTO
import com.gugu.dts.playlist.core.entity.MusicLibrary
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CommanderTest {

    @MockK
    lateinit var libraryRepository: MusicLibraryRepository

    @MockK
    lateinit var groupRepository: FilterGroupRepository

    @MockK
    lateinit var library: MusicLibrary

    lateinit var commander: Commander

    @BeforeEach
    fun initMock() {
        every { libraryRepository.import(any()) } returns library
        commander = Commander(libraryRepository, groupRepository)
    }

    @Test
    fun importLibray() {
        val lib = commander.importLibray(LibraryDTOImpl(
                "D:/test",
                "test library",
                listOf(
                        SongDTOImpl("test1.mp3", "D:/test/test1.mp3", 100.0, 100),
                        SongDTOImpl("test2.mp3", "D:/test/test2.mp3", 110.0, 100),
                        SongDTOImpl("test3.mp3", "D:/test/test3.mp3", 120.0, 100),
                        SongDTOImpl("test4.mp3", "D:/test/test4.mp3", 130.0, 100)
                )
        ))
        assertNotNull(lib)
    }

    @Test
    fun createRule(){

    }

}

private class LibraryDTOImpl(override val path: String, override val name: String, override val songs: List<ISongDTO>) : IMusicLibraryDTO
private class SongDTOImpl(override val name: String, override val path: String, override val bpm: Double, override val trackLength: Int, override val artist: String = "default", override val album: String? = null) : ISongDTO