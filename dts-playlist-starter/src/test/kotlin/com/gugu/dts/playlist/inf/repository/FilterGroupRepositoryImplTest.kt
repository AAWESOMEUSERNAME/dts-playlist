package com.gugu.dts.playlist.inf.repository

import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.starter.TestApplication
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [TestApplication::class])
@Transactional(rollbackFor = [Throwable::class])
@Rollback
internal class FilterGroupRepositoryImplTest {

    @Autowired
    lateinit var repo: FilterGroupRepository

    @Test
    fun list() {
        val list = repo.list()
        assertTrue(true)
    }

    @Test
    fun find() {
        val find = repo.find(1)
        assertTrue(true)
    }
}