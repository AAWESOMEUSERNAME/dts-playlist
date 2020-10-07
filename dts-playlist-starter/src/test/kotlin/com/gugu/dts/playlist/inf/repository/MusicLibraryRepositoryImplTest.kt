package com.gugu.dts.playlist.inf.repository

import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import com.gugu.dts.playlist.starter.TestApplication
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat

@SpringBootTest(classes = [TestApplication::class])
@Transactional(rollbackFor = [Throwable::class])
@Rollback
internal class MusicLibraryRepositoryImplTest {

    @Autowired
    lateinit var libraryRepo: MusicLibraryRepository

    init {
    }

    @Test
    fun fetchLibraryByName() {
        libraryRepo.fetchLibraryByName("")
    }

    @Test
    fun list() {
        val list = libraryRepo.list()

        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(list[0].createAt)
        println(format)
    }

    @Test
    fun import() {
    }

}