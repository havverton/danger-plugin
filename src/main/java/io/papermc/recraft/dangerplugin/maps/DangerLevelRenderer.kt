package io.papermc.recraft.dangerplugin.maps

import io.papermc.recraft.dangerplugin.Danger
import org.bukkit.Chunk
import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapPalette
import org.bukkit.map.MapPalette.matchColor
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.roundToInt

object DangerLevelRenderer : MapRenderer(false) {

    val chunks = mutableMapOf<Long, Double>().withDefault { 0xBAD.toDouble() }

    @Suppress("DEPRECATION")
    val dangerLevel = listOf(
            matchColor(0, 255, 255),
            matchColor(0, 185, 49),
            matchColor(0, 214, 57),
            matchColor(153, 204, 3),
            matchColor(255, 255, 255),
            matchColor(255, 255, 1),
            matchColor(212, 203, 65),
            matchColor(184, 131, 36),
            matchColor(213, 125, 50),
            matchColor(217, 0, 0),
            matchColor(178, 0, 0),
            matchColor(95, 0, 0)

    )


    fun clear(map: MapView, canvas: MapCanvas, player: Player) {
        val (world) = map.renderers
        world.render(map, canvas, player)
    }


    override fun render(map: MapView, canvas: MapCanvas, player: Player) {
        map.centerZ = player.location.blockZ
        map.centerX = player.location.blockX
        clear(map, canvas, player)
        map.scale = MapView.Scale.CLOSE
        val blockPerPixel = when (map.scale) {
            MapView.Scale.CLOSEST -> 1
            MapView.Scale.CLOSE -> 2
            MapView.Scale.NORMAL -> 4
            MapView.Scale.FAR -> 8
            MapView.Scale.FARTHEST -> return
        }
        val pixelsPerChunk = 16 / blockPerPixel
        val chunkCount = 128 / pixelsPerChunk
        val offsetX = 16 - (map.centerX % 16 / blockPerPixel)
        val offsetZ = 16 - (map.centerZ % 16 / blockPerPixel)
        val centerChunkX: Int = map.centerX shr 4
        val centerChunkZ: Int = map.centerZ shr 4


        // Chunk borders
        (-2..chunkCount).forEach { x ->
            (-2..chunkCount).forEach { z ->
                (0..pixelsPerChunk).forEach { i ->
                    canvas.setPixel(x * pixelsPerChunk + i + offsetX, z * pixelsPerChunk + offsetZ, 22)
                    canvas.setPixel(x * pixelsPerChunk + offsetX, z * pixelsPerChunk + i + offsetZ, 22)
                }
            }
        }


        // Crosses
        (-2..chunkCount).forEach { x ->
            (-2..chunkCount).forEach { z ->
                canvas.setPixel(x * pixelsPerChunk + offsetX, z * pixelsPerChunk + offsetZ, MapPalette.TRANSPARENT)
                val chunkX = 1 + (centerChunkX - chunkCount / 2) + x
                val chunkZ = 1 + (centerChunkZ - chunkCount / 2) + z
                val chunkKey = Chunk.getChunkKey(chunkX, chunkZ)
                Danger.getLevel(player.world.name, chunkKey) {
                    chunks[chunkKey] = it
                }
                val dangerIndex = chunks.getValue(chunkKey).roundToInt()

                if (dangerIndex != 0xBAD) {
                    val dangerColor = dangerLevel[4 + max(-4, min(dangerIndex, 7))]

                    val pX = x * pixelsPerChunk + pixelsPerChunk / 2 + offsetX
                    val pZ = z * pixelsPerChunk + pixelsPerChunk / 2 + offsetZ
                    (-2..2).forEach { cross ->
                        canvas.setPixel(pX + cross, pZ, dangerColor)
                        canvas.setPixel(pX, pZ + cross, dangerColor)
                    }
                }

            }
        }


    }
}

