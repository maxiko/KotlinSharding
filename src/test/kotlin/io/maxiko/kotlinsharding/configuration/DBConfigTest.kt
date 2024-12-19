package io.maxiko.kotlinsharding.configuration

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DBConfigTest {

    @Autowired
    lateinit var dbConfig: DBConfig

    @Test
    fun testConfiguration() {
        SoftAssertions.assertSoftly {
            it.assertThat(dbConfig).isNotNull
            it.assertThat(dbConfig.shards.size).isEqualTo(2)
        }
    }
}