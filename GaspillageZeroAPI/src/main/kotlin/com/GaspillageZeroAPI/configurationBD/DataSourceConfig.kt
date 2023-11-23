package com.GaspillageZeroAPI.configurationBD

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.jdbc.DataSourceBuilder
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    fun getDataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url("jdbc:mysql://localhost:3306/gaspillagealimentaire?useSSL=false")
            .username("root")
            .password("password")
            .build()
    }
}
