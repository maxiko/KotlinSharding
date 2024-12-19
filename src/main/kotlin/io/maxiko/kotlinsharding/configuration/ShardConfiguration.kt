package io.maxiko.kotlinsharding.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.Properties


@Configuration
@EnableJpaRepositories(basePackages = ["io.maxiko.kotlinsharding.dto"])
@EntityScan(basePackages = ["io.maxiko.kotlinsharding.dto"])
class ShardConfiguration(
    val env: Environment,
    val dbConfig: DBConfig
) {

    @Bean
    fun multiRoutingDataSource() =
        RoutingDataSource()
            .also {
                it.setTargetDataSources(
                    dbConfig.shards
                        .mapKeys { entry -> ShardEnum.from(entry.key)!! }
                        .mapValues { entry -> entry.value.toDataSource() }
                )
            }

    @Bean
    fun entityManagerFactory() = LocalContainerEntityManagerFactoryBean().also {
        it.dataSource = multiRoutingDataSource()
        it.jpaVendorAdapter = HibernateJpaVendorAdapter()
        it.setPackagesToScan("io.maxiko.kotlinsharding.dto")
        it.setJpaProperties(
            Properties().also { properties ->
                properties["hibernate.hbm2ddl.auto"] = env.getProperty("spring.jpa.hibernate.ddl-auto")
                properties["hibernate.show-sql"] = env.getProperty("spring.jpa.show-sql")
            }
        )
    }

    @Bean
    fun transactionManager() =
        entityManagerFactory().`object`?.let { JpaTransactionManager(it) }

}