package io.papermc.recraft.dangerplugin.listeners

import io.papermc.paper.world.MoonPhase
import org.bukkit.Material
import org.bukkit.entity.Monster
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBedEnterEvent

object SleepListener : Listener {

    @EventHandler
    fun on(event: PlayerBedEnterEvent) {
        val player = event.player
        val bed = event.bed
        //блок проверки монстров в округе
        val entities = player.getNearbyEntities(16.0, 5.0, 16.0)
        if (entities.any { entity -> entity is Monster }) {
            event.isCancelled = true
            return
        }
        //блок проверки времени
        val currentTime = player.world.time
        if (currentTime <= 14000) {
            player.sendActionBar("Спать можно только ночью")
            event.isCancelled = true
            return
        }
        //блок проверки на фазу луны
        if (player.world.moonPhase == MoonPhase.FULL_MOON) {
            player.sendActionBar("Вы не можете спать при полной луне")
            event.isCancelled = true
            return
        }
        val ligthLevel = bed.lightFromBlocks
        //блок проверки освещённости
        if (ligthLevel < 7) {
            event.isCancelled = true
            player.sendActionBar("Здесь слишком опасно чтобы спать")
            return
        }

        //блок проверки на открытое небо
        var flag = true
        for (i in 1..32) {
            val loc = bed.location
            loc.add(0.0, i.toDouble(), 0.0)
            if (loc.block.type != Material.AIR) {
                flag = false
                break
            }
            player.sendActionBar("Здесь слишком опасно чтобы спать")

        }

        if (flag) {
            event.isCancelled = true
        }
    }

}
