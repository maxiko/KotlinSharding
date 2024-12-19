package io.maxiko.kotlinsharding.configuration

enum class ShardEnum(val value: String) {

    SHARD1("shard1"),
    SHARD2("shard2");

    companion object {
        private val map = entries.associateBy { it.value }
        infix fun from(value: String) = map[value]
    }

}