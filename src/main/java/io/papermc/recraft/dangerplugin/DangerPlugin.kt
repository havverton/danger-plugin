package io.papermc.recraft.dangerplugin

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection

import io.recraft.api.ReCraft
import io.recraft.api.ReCraftPlugin
import io.papermc.recraft.dangerplugin.listeners.SleepListener
import io.papermc.recraft.dangerplugin.systems.DangerSystem



class DangerPlugin : ReCraftPlugin() {
    companion object {
        lateinit var plugin: DangerPlugin
            private set
    }

    var lock: Boolean = false
    lateinit var redis: StatefulRedisConnection<String, String>
        private set


    override fun onLoad() {
        plugin = this
        saveDefaultConfig()

        val redisUrl: String =
            System.getenv("REDIS_URL") ?: config.getString("REDIS_URL") ?: "redis://password@localhost:6379/0"
        val redisClient: RedisClient = RedisClient.create(redisUrl)
        redis = redisClient.connect()

    }

    /*
    @Suppress("DEPRECATION")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (args.firstOrNull() == "info") {
            val chunk = sender.location.chunk
            sender.sendMessage("§aChunk Info (${chunk.x}, ${chunk.z})")
            sender.sendMessage("§2 Danger Level: ${Danger.getLevel(chunk)}")
            sender.sendMessage("§2 Night Danger Level: ${getNightDanger(chunk)}")
            sender.sendMessage("§2 Max Mobs Count: ${getMaxMobsInChunkCount(chunk, CreatureType.MONSTER)}")
            sender.sendMessage("§2 Max Animals Count: ${getMaxMobsInChunkCount(chunk, CreatureType.ANIMAL)}")

            sender.sendMessage(
                "§2 Monsters: ${
                    getEntitiesOfType(
                        sender.location,
                        CreatureType.MONSTER
                    ).size
                }     Animals: ${getEntitiesOfType(sender.location, CreatureType.ANIMAL).size}"
            )

            return true
        }
        val blockLooking = sender.rayTraceBlocks(32.0)?.hitBlock
        if (blockLooking != null) {
            val creatureType = CreatureType.valueOf(args.firstOrNull()?.toUpperCase() ?: "MONSTER")
            val spawned = spawnEntity(blockLooking.location.add(.0, 1.0, .0), creatureType)
            if (!spawned) {
                sender.sendActionBar("Сущность не была создана")
            }
        } else {
            sender.sendActionBar("Вы должны смотреть на блок")
        }
        return true
    }
    */

    override fun onEnable() { // Plugin startup logic
        ReCraft.addSystem(DangerSystem(this))
        //ReCraft.addSystem(GoldDropSystem(this))
        //ReCraft.addSystem(MobTargetSystem(this))

        server.pluginManager.registerEvents(SleepListener, plugin)
    }

    override fun onDisable() { // Plugin shutdown logic
    }
}

