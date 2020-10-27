package com.gugu.dts.playlist.starter.autoconfig

import com.gugu.dts.playlist.api.ICommander
import com.gugu.dts.playlist.api.IQuery
import com.gugu.dts.playlist.core.repository.FilterGroupRepository
import com.gugu.dts.playlist.core.repository.MusicLibraryRepository
import com.gugu.dts.playlist.core.service.Commander
import com.gugu.dts.playlist.core.service.Query
import com.gugu.dts.playlist.inf.autoconfig.PlayListInfAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@AutoConfigureAfter(PlayListInfAutoConfiguration::class)
class PlayListStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ICommander::class)
    fun iCommander(libraryRepo: MusicLibraryRepository): ICommander {
        return Commander(libraryRepo)
    }

    @Bean
    @ConditionalOnMissingBean(IQuery::class)
    fun iQuery(libraryRepo: MusicLibraryRepository, filterGroupRepo: FilterGroupRepository): IQuery {
        return Query(libraryRepo, filterGroupRepo)
    }

}