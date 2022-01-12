//package io.recraft.danger.systems
//
//import io.recraft.actions.Action
//import io.recraft.actions.EntityDeathPayload
//import io.recraft.economy.Economy
//import io.recraft.ecs.ECSComponent
//import io.recraft.ecs.ECSSystem
//import org.bukkit.attribute.Attribute
//import org.bukkit.entity.Monster
//import org.bukkit.event.entity.CreatureSpawnEvent
//import org.bukkit.plugin.java.JavaPlugin
//import kotlin.random.Random
//
//class GoldDropSystem(plugin: JavaPlugin) : ECSSystem(plugin) {
//    override val componentTypes: List<Class<out ECSComponent>> = emptyList()
//
//    init {
//        on(Action.ENTITY_DEATH) { payload: EntityDeathPayload ->
//            val entity = payload.entity
//            val bukkitEntity = entity.getBukkitEntity()
//            if (bukkitEntity is Monster) {
//                val killer = bukkitEntity.killer
//
//                // TODO Replace with tag system
//                if (killer != null && !bukkitEntity.hasMetadata("DaylightFire") && bukkitEntity.entitySpawnReason != CreatureSpawnEvent.SpawnReason.SPAWNER) {
//                    val maxHealth = bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
//                    Economy.dropGold(bukkitEntity.location, (1 + maxHealth / 9).toInt() + Random.nextInt(4))
//                }
//            }
//        }
//    }
//}
