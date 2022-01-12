//package io.recraft.danger.systems
//
//import io.recraft.danger.DangerPlugin
//import io.recraft.ecs.ECSComponent
//import io.recraft.ecs.ECSSystem
//import org.bukkit.entity.EntityType
//import org.bukkit.entity.Monster
//import org.bukkit.event.EventHandler
//import org.bukkit.event.entity.EntityTargetEvent
//import org.bukkit.event.entity.EntityTargetLivingEntityEvent
//
//class MobTargetSystem(plugin: DangerPlugin) : ECSSystem(plugin) {
//    override val componentTypes: List<Class<out ECSComponent>> = listOf()
//
//    @EventHandler
//    fun on(event: EntityTargetLivingEntityEvent) {
//        when (event.reason) {
//            EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY -> {
//                if (event.target is Monster && event.entity is Monster && event.entity.type != EntityType.CREEPER) {
//                    event.isCancelled = true
//                }
//            }
//            else -> {
//            }
//        }
//    }
//
//
//}
