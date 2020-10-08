package com.gugu.dts.playlist.inf.autoconfig

import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Level
import java.util.logging.Logger
import javax.sql.DataSource


@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration::class)
class PlayListDataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DataSource::class)
    fun dataSource(): DataSource {
        val logger = Logger.getLogger("app")

        val path = System.getProperty("user.dir") + "/dts-playlist.db"
        logger.log(Level.INFO, "db path = $path")

        val builder = DataSourceBuilder.create()
        builder.url("jdbc:sqlite:$path")
        return builder.build()
    }
}