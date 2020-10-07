package com.gugu.dts.playlist.inf.autoconfig

import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import com.gugu.dts.playlist.inf.mapper.MusicLibraryMapper
import com.gugu.dts.playlist.inf.mapper.SongMapper
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import repository.MusicLibraryRepositoryImpl

@Configuration
@MapperScan("com.gugu.dts.playlist.inf.mapper")
@AutoConfigureAfter(value = [MybatisAutoConfiguration::class])
class PlayListInfAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(MusicLibraryRepository::class)
    fun musicLibraryRepository(libraryMapper: MusicLibraryMapper, songMapper: SongMapper): MusicLibraryRepository {
        return MusicLibraryRepositoryImpl(libraryMapper, songMapper)
    }
}