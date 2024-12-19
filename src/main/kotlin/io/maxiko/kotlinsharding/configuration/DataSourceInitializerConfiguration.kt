package io.maxiko.kotlinsharding.configuration

import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import javax.sql.DataSource

/**
 * Динамически создаем бины типа [DataSourceInitializer] для инициализации каждого шарда
 */
@Configuration
class DataSourceInitializerConfiguration {

    @Bean
    fun beanDefinitionRegistrar(env: Environment) = BeanDefinitionRegistrar(env)

    class BeanDefinitionRegistrar(
        private val environment: Environment
    ) : BeanDefinitionRegistryPostProcessor {

        private fun getDataSourceInitializer(dataSource: DataSource) =
            DataSourceInitializer().also {
                it.setDataSource(dataSource)
            }

        override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
            val dbConfig = Binder
                .get(environment)
                .bind("sharding", DBConfig::class.java)
                .orElseThrow { IllegalStateException("Sharding configuration can't be initialized.") }

            dbConfig.shards.forEach { shard ->
                registry.registerBeanDefinition(
                    "${shard.key}DataSourceInitializer",
                    GenericBeanDefinition().also {
                        it.beanClassName = "${shard.key}DataSourceInitializer"
                        it.setBeanClass(DataSourceInitializer::class.java)
                        it.setInstanceSupplier({ getDataSourceInitializer(shard.value.toDataSource()) })
                    }
                )
            }
        }

    }

}