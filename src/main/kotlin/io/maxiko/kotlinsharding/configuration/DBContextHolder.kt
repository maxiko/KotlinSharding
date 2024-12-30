package io.maxiko.kotlinsharding.configuration

/**
 * Контекст в котором хранится текущий шард
 */
class DBContextHolder {

    companion object {

        private var contextHolder = ThreadLocal<ShardEnum>().also {
            it.set(ShardEnum.SHARD1)
        }

        fun setCurrentDbContext(shard: ShardEnum) {
            contextHolder.set(shard)
        }

        fun getCurrentDbContext(): ShardEnum = contextHolder.get()

    }

}