package example.com.utils

import com.typesafe.config.ConfigFactory
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

object RedisConfig {
    private val baseUrl = System.getenv("REDIS_BASE_URL") ?: ""
    private var pool = JedisPool(baseUrl)

    val jedis: Jedis = pool.resource
}