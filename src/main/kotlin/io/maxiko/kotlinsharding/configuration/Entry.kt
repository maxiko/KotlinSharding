package io.maxiko.kotlinsharding.configuration

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import javax.sql.DataSource

data class Entry(
    val datasource: DataSourceProperties
) {

    fun toDataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(datasource.driverClassName)
            .url(datasource.url)
            .username(datasource.username)
            .password(datasource.password)
            .build()
}
