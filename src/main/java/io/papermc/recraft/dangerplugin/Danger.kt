package io.papermc.recraft.dangerplugin

import com.google.common.cache.CacheBuilder
import org.bukkit.Chunk
import org.bukkit.Location
import kotlin.math.abs
import kotlin.random.Random

object Danger {
    val plugin: DangerPlugin
        get() = DangerPlugin.plugin

    private val sync
        get() = plugin.redis.sync()

    private val async
        get() = plugin.redis.async()

    private const val DEFAULT_DANGER_VALUE = 2.5

    val cache = CacheBuilder
            .newBuilder()
            .build<Pair<String, Long>, Double>().asMap().withDefault { DEFAULT_DANGER_VALUE }


    fun getLevel(world: String, chunkKey: Long, consumer: (Double) -> Unit) {
        async.hget("danger-$world", chunkKey.toString()).thenAccept { answer ->
            consumer(answer?.toDouble() ?: DEFAULT_DANGER_VALUE)
        }
    }

    fun getLastLoadTime(chunk: Chunk, consumer: (time: Long, timeSince: Long) -> Unit) {
        async.hget("load-${chunk.world.name}", chunk.chunkKey.toString()).thenAccept { answer: String? ->
            val now = System.currentTimeMillis() / 1000L
            if (answer == null) {
                consumer(now, 0)
            } else {
                val time = answer.toLong()
                val diff = time - now;
                consumer(time, diff)
            }

        }
    }

    fun getLastLoadTime(chunk: Chunk): Long {
        val hget: String? = sync.hget("load-${chunk.world.name}", chunk.chunkKey.toString())
        return hget?.toLong() ?: System.currentTimeMillis() / 1000L
    }

    fun updateLastLoadTime(chunk: Chunk) {
        val time = System.currentTimeMillis() / 1000L
        async.hset("load-${chunk.world.name}", chunk.chunkKey.toString(), time.toString())
    }

    fun getLevel(world: String, chunkKey: Long): Double {
        return cache.getValue("danger-$world" to chunkKey)
//        return sync.hget("danger-$world", chunkKey.toString())?.toDouble() ?: DEFAULT_DANGER_VALUE
    }

    fun getLevel(chunk: Chunk): Double {
        return getLevel(chunk.world.name, chunk.chunkKey)
    }

    fun getLevel(location: Location): Double = getLevel(location.chunk)

    fun getLevel(chunk: Chunk, consumer: (Double) -> Unit) = getLevel(chunk.world.name, chunk.chunkKey, consumer)

    fun getLevel(location: Location, consumer: (Double) -> Unit) = getLevel(location.chunk, consumer)

    fun increase(world: String, chunkKey: Long, value: Double) {
        val worldKey = "danger-$world"
        val defaultValue = Random.nextDouble(1.9, 3.4)
        val key = worldKey to chunkKey

        cache.putIfAbsent(key, defaultValue)
        cache[key] = cache.getValue(key) + value

        with(async) {
            hsetnx(worldKey, chunkKey.toString(), defaultValue.toString())
            hincrbyfloat(worldKey, chunkKey.toString(), value)
        }
    }

    fun increase(chunk: Chunk, value: Double) = increase(chunk.world.name, chunk.chunkKey, value)

    fun increase(location: Location, value: Double) = increase(location.world.name, location.chunk.chunkKey, value)

    fun increaseRadius(world: String, chunkX: Int, chunkZ: Int, value: Double, radius: Int = 4) {
        async.multi()
        (-radius..radius).forEach { x ->
            (-radius..radius).forEach { z ->
                val chunkKey = Chunk.getChunkKey(chunkX + x, chunkZ + z)
                val k = 1 + abs(x) + abs(z)
                increase(world, chunkKey, value / k)
            }
        }
        async.exec()
    }


}
