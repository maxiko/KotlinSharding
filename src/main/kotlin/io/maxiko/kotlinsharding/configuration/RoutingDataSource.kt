package io.maxiko.kotlinsharding.configuration

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * Класс осуществляющий маршрутизацию на конкретный шард
 */
class RoutingDataSource: AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): ShardEnum = DBContextHolder.getCurrentDbContext()
}