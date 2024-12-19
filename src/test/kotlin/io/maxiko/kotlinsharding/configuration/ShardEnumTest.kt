package io.maxiko.kotlinsharding.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShardEnumTest {

    @Test
    fun testEnum(){
        val enum = ShardEnum.from("shard1")

        assertThat(enum).isEqualTo(ShardEnum.SHARD1)
    }
}