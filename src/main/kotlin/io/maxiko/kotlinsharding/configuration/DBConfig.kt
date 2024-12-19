package io.maxiko.kotlinsharding.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sharding")
data class DBConfig(
    val shards: Map<String, Entry>
)
