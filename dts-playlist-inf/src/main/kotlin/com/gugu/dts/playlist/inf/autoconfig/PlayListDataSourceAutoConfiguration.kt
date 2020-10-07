package com.gugu.dts.playlist.inf.autoconfig

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration::class)
class PlayListDataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DataSource::class)
    fun dataSource(): DataSource {

        val builder = DataSourceBuilder.create()
        builder.url("jdbc:sqlite:" + ClassLoader.getSystemResource("db/dts-playlist.db").path)
        return builder.build()
    }
}