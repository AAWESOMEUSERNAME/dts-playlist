package com.gugu.dts.playlist.core.service

import com.gugu.dts.playlist.core.entity.MusicLibrary
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class QueryTest {

    @MockK
    lateinit var libraryRepo: MusicLibraryRepository

    @MockK
    lateinit var libMock: MusicLibrary
    lateinit var libMocks: List<MusicLibrary>

    lateinit var query: Query


    @BeforeEach
    fun init() {
        every { libMock.name } returns "test"
        every { libMock.path } returns "D:/test"
        every { libraryRepo.fetchLibraryByName("test") } returns libMock

        libMocks = listOf(libMock)
        every { libraryRepo.list() } returns libMocks

        query = Query(libraryRepo)
    }

    @Test
    fun fetchLibraryByName() {
        val lib = query.fetchLibraryByName("test")

        assertEquals("test", lib?.name)
        assertEquals("D:/test", lib?.path)
    }

    @Test
    fun listLibrary() {
        val libs = query.listLibrary()

        assertEquals(1, libs.size)
        assertEquals("test", libs[0].name)
    }
}