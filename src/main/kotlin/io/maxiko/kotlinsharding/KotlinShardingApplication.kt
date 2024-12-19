package io.maxiko.kotlinsharding

import io.maxiko.kotlinsharding.configuration.DBConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DBConfig::class)
class KotlinShardingApplication

fun main(args: Array<String>) {
    runApplication<KotlinShardingApplication>(*args)
}
