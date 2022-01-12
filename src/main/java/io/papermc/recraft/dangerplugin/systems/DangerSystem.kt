package io.papermc.recraft.dangerplugin.systems

import com.google.common.cache.CacheBuilder
import com.google.common.math.DoubleMath
import  io.papermc.recraft.dangerplugin.Danger
import io.papermc.recraft.dangerplugin.DangerPlugin
import io.papermc.recraft.dangerplugin.maps.DangerLevelRenderer
import io.recraft.ecs.ECSComponent
import io.recraft.ecs.ECSEntity
import io.recraft.ecs.ECSSystem
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.WorldInitEvent
import org.bukkit.inventory.meta.MapMeta
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import kotlin.math.abs


class DangerSystem(plugin: DangerPlugin) : ECSSystem(plugin) {
    override val componentTypes: List<Class<out ECSComponent>> = listOf()

    init {
        plugin.getCommand("danger")?.setExecutor(::onCommand)
    }

    val localDanger = CacheBuilder
        .newBuilder()
        .expireAfterWrite(12, TimeUnit.MINUTES)
        .build<Pair<String, Long>, Double>().asMap()
        .withDefault { (world, chunkKey) -> Danger.getLevel(world, chunkKey) }

    val animalness = CacheBuilder
        .newBuilder()
        .expireAfterWrite(60, TimeUnit.MINUTES)
        .build<Pair<String, Long>, Double>().asMap()
        .withDefault { (world, chunkKey) -> (4.5 - Danger.getLevel(world, chunkKey)).coerceIn(0.0, 4.0) }

    fun getNightDanger(chunk: Chunk): Double {
        return localDanger.getValue(chunk.world.name to chunk.chunkKey)
    }

    fun getAnimalness(chunk: Chunk): Double {
        return animalness.getValue(chunk.world.name to chunk.chunkKey)
    }

    fun increaseNightDanger(chunk: Chunk, value: Double, radius: Int) {
        (-radius..radius).forEach { x ->
            (-radius..radius).forEach { z ->
                val chunkX: Int = chunk.x + x
                val chunkZ: Int = chunk.z + z
                val chunkKey = Chunk.getChunkKey(chunkX, chunkZ)
                val k = 1 + abs(x) + abs(z)
                val key = chunk.world.name to chunkKey
                localDanger[key] = localDanger.getValue(key) + value / k
            }
        }
    }

    fun increaseAnimalness(chunk: Chunk, value: Double, radius: Int) {
        (-radius..radius).forEach { x ->
            (-radius..radius).forEach { z ->
                val chunkX: Int = chunk.x + x
                val chunkZ: Int = chunk.z + z
                val chunkKey = Chunk.getChunkKey(chunkX, chunkZ)
                val k = 1 + abs(x) + abs(z)
                val key = chunk.world.name to chunkKey
                animalness[key] = animalness.getValue(key) + value / k
            }
        }
    }


    fun getMaxMobsInChunkCount(chunk: Chunk, type: CreatureType): Int {
        return when (type) {
            CreatureType.MONSTER -> {
                val value = DoubleMath.roundToInt(getNightDanger(chunk), RoundingMode.HALF_DOWN)
                when {
                    value > 5 -> value - 3
                    else -> value
                }
            }
            CreatureType.ANIMAL -> {
                val value = DoubleMath.roundToInt(getAnimalness(chunk), RoundingMode.HALF_UP)
                value
            }
        }
    }

    private fun getEntitiesOfType(chunk: Chunk, creatureType: CreatureType): List<Entity> {
        val isMonster = creatureType == CreatureType.MONSTER

        return chunk.entities.filter {
            isMonster == (it::class.java.superclass == Monster::class.java)
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun on(event: CreatureSpawnEvent) {
        if (event.isCancelled) return

        val type = event.entityType
        val entityClass = type.entityClass?.kotlin ?: throw Error("No entity class found for type $type")
        val isMonster = (entityClass::class.java.superclass == Monster::class.java)

        val chunk = event.location.chunk

        val mobType = if (isMonster) CreatureType.MONSTER else CreatureType.ANIMAL
        val maxMobCount = getMaxMobsInChunkCount(chunk, mobType)
        val currentEntities = getEntitiesOfType(chunk, mobType)

        if (currentEntities.size >= maxMobCount) {
            event.isCancelled = true
            return
        }
        if (isMonster)
            increaseNightDanger(chunk, -0.01, 4)
        else
            increaseAnimalness(chunk, -0.5, 3)
    }

    @EventHandler
    fun on(event: EntityDeathEvent) {
        val entity = event.entity
        val killer = entity.lastDamageCause?.entity
        val chunk = entity.chunk

        when {
            Danger.plugin.lock -> return
            killer == null -> return
            killer is Raider -> {
                val value = when (event.entityType) {
                    EntityType.PLAYER -> 0.1
                    else -> 0.05
                }
                Danger.increaseRadius(entity.world.name, chunk.x, chunk.z, value)
            }
            killer is Player -> {
                val value = when (event.entityType) {
                    EntityType.PLAYER -> 0.05
                    else -> -0.02
                }
                Danger.increaseRadius(entity.world.name, chunk.x, chunk.z, value)
            }
        }
    }

    @Suppress("DEPRECATION")
    fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        val world = sender.world.name
        when (args.firstOrNull()) {
            "map" -> {
                val item = sender.inventory.itemInMainHand
                if (item.type != Material.FILLED_MAP) {
                    sender.sendActionBar("Необходимо держать в руке карту.")
                    return true
                }

                val mapMeta = item.itemMeta as MapMeta
                val mapView = mapMeta.mapView
                if (mapView == null) {
                    sender.sendActionBar("Карта не должна быть пустой.")
                    return true
                } else {
                    mapView.addRenderer(DangerLevelRenderer)
                }
            }
            "info" -> {
                sender.sendMessage(sender.location.block.type.name)
            }
            "lock" -> {
                Danger.plugin.lock = !Danger.plugin.lock
                sender.sendActionBar("Блокировка уровня опасности: " + if (Danger.plugin.lock) "включена" else "отключена")
            }
//            "geography" -> {
//                sender.sendActionBar(sender.location.block.getGeography().name)
//            }
            "purge" -> {
                Danger.plugin.redis.async().del("danger-$world")
                localDanger.clear()
                animalness.clear()
                Danger.cache.clear()
                sender.sendActionBar("Данные уровня опасности мира $world удалены.")
            }
            "boom" -> {
                val amount = args[1].toDouble()
                val radius = args.getOrElse(2) { "4" }.toInt()
                val chunk = sender.location.chunk
                sender.sendActionBar("Boom")
                Danger.increaseRadius(world, chunk.x, chunk.z, amount, radius)
            }
            null -> {
            }
        }

        return true
    }

    override fun update(entities: List<ECSEntity>) {
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    fun on(event: WorldInitEvent) {
        val sync = Danger.plugin.redis.sync()
        val worldKey = "danger-${event.world.name}"
        val hgetall: MutableMap<String, String> = sync.hgetall(worldKey)
        val mapKeys = hgetall
            .mapKeys { worldKey to it.key.toLong() }
            .mapValues { it.value.toDouble() }
        Danger.cache.putAll(mapKeys)
    }


}

enum class CreatureType {
MONSTER,ANIMAL
}
