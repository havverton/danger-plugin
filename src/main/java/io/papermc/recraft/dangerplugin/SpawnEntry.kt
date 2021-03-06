package io.recraft.danger

/*
class SpawnEntry(
    val type: String,
    val geographies: List<Geography>,
    val weight: Double,
    val dangerRange: DoubleRange = DoubleRange(-3, 7),
    val predicates: List<(Location) -> Boolean> = emptyList()
) {
    constructor(
        type: EntityTemplate<*>,
        geographies: List<Geography>,
        weight: Double,
        danger: IntRange = -3..7,
        predicates: List<(Location) -> Boolean> = emptyList()
    ) : this(type.type, biomeTypes, weight, DoubleRange(danger.first, danger.last), predicates)

    companion object {
        val isCave = { location: Location -> location.block.type == Material.CAVE_AIR }

        val NOT_IN_WATER = Geography.values() - Geography.OCEAN


        // Spawn Entries
        val values = listOf(
            // PLAINS, FOREST, BIRCH_FOREST
            SpawnEntry(EntityTemplate.DROWNED, listOf(Geography.OCEAN, Geography.BEACH), 1.0, -1..3),

            SpawnEntry(
                EntityTemplate.ZOMBIE,
                listOf(Geography.PLAINS, Geography.FOREST, Geography.SAVANNA),
                1.0,
                1..5
            ),
            SpawnEntry(
                EntityTemplate.SKELETON,
                listOf(Geography.PLAINS, Geography.FOREST, Geography.SAVANNA),
                1.0,
                2..5
            ),
//            SpawnEntry(
//                EntityTemplate.CREEPER,
//                listOf(BiomeType.PLAINS, BiomeType.FOREST, BiomeType.BIRCH_FOREST),
//                1.0,
//                3..4
//            ),
//            SpawnEntry(
//                EntityTemplate.SPIDER,
//                listOf(BiomeType.PLAINS, BiomeType.FOREST, BiomeType.BIRCH_FOREST),
//                1.0,
//                1..5
//            ),
//            SpawnEntry(
//                EntityTemplate.PIG,
//                listOf(BiomeType.PLAINS, BiomeType.FOREST, BiomeType.BIRCH_FOREST),
//                1.0,
//                -4..3
//            ),
//            SpawnEntry(
//                EntityTemplate.COW,
//                listOf(BiomeType.PLAINS, BiomeType.FOREST, BiomeType.BIRCH_FOREST),
//                1.0,
//                -4..3
//            ),
//            SpawnEntry(
//                EntityTemplate.SHEEP,
//                listOf(BiomeType.PLAINS, BiomeType.FOREST, BiomeType.BIRCH_FOREST),
//                1.0,
//                -4..3
//            ),
//
//
//            //  BiomeType.MOUNTAINS
//            SpawnEntry(EntityTemplate.LYNX, listOf(BiomeType.MOUNTAINS), 1.0, -0..3),
//            SpawnEntry(EntityTemplate.COW, listOf(BiomeType.MOUNTAINS), 1.0, -4..3),
//            SpawnEntry(EntityTemplate.SHEEP, listOf(BiomeType.MOUNTAINS), 1.0, -4..3),
//
//
//            // SWAMP
//            // ??????????
//            SpawnEntry(EntityTemplate.ROTFIEND, listOf(BiomeType.SWAMP), 1.0, 0..7),
//            SpawnEntry(EntityTemplate.SWAMP_SPIDER, listOf(BiomeType.SWAMP), 1.0, 0..7),
//            SpawnEntry(EntityTemplate.SWAMP_WITCH, listOf(BiomeType.SWAMP), 1.0, 0..7),
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.FROG, listOf(BiomeType.SWAMP), 1.0, -2..7),
//            SpawnEntry(EntityTemplate.SLIME, listOf(BiomeType.SWAMP), 1.0, -2..7),
//            SpawnEntry(EntityTemplate.PIG, listOf(BiomeType.SWAMP), 1.0, -2..7),
//            SpawnEntry(EntityTemplate.BAT, listOf(BiomeType.SWAMP), 1.0, -2..7),
//
//
//            // BiomeType.DESERT
//            // ??????????
//            SpawnEntry(EntityTemplate.HUSK, listOf(BiomeType.DESERT), 1.0, 1..4),
//            SpawnEntry(EntityTemplate.MUMMY, listOf(BiomeType.DESERT), 1.0, 3..6),
//            SpawnEntry(EntityTemplate.DESERT_CREEPER, listOf(BiomeType.DESERT), 1.0, 2..5),
//            SpawnEntry(EntityTemplate.DESERT_SPIDER, listOf(BiomeType.DESERT), 1.0, 0..4),
//            SpawnEntry(EntityTemplate.SCORPIO, listOf(BiomeType.DESERT), 1.0, 2..7),
//            SpawnEntry(EntityTemplate.DESERT_CULTIST, listOf(BiomeType.DESERT), 1.0, 2..4),
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.RABBIT, listOf(BiomeType.DESERT), 1.0, -4..2), // ????????????
//            SpawnEntry(EntityTemplate.CAMEL, listOf(BiomeType.DESERT), 1.0, -4..3), // ??????????????
//
//            // SAVANNA
//            // ??????????
//            SpawnEntry(EntityTemplate.HUSK, listOf(BiomeType.SAVANNA), 1.0, -2..4),
//            SpawnEntry(EntityTemplate.SPIDER, listOf(BiomeType.SAVANNA), 1.0, 0..4),
//            SpawnEntry(EntityTemplate.SKELETON, listOf(BiomeType.SAVANNA), 1.0, -2..4),
//            SpawnEntry(EntityTemplate.CREEPER, listOf(BiomeType.SAVANNA), 1.0, -2..4),
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.RABBIT, listOf(BiomeType.SAVANNA), 1.0, -2..3),// ????????????
//            SpawnEntry(EntityTemplate.LLAMA, listOf(BiomeType.SAVANNA), 1.0, -2..3), // ????????
//            SpawnEntry(EntityTemplate.ZEBRA, listOf(BiomeType.SAVANNA), 1.0, -2..3), // ??????????
//            SpawnEntry(EntityTemplate.HYENA, listOf(BiomeType.SAVANNA), 1.0, 1..4),// ??????????
//
//
//            // TAIGA, COLD_TAIGA
//            SpawnEntry(EntityTemplate.DEER, listOf(BiomeType.TAIGA, BiomeType.COLD_TAIGA), 1.0, -2..3), // ??????????
//            SpawnEntry(
//                EntityTemplate.BROWN_BEAR,
//                listOf(BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0,
//                1..4
//            ), // ?????????? ??????????????
//
//
//            // FROZEN_RIVER, MOUNTAINS, TAIGA, COLD_TAIGA
//            // ??????????
//            SpawnEntry(
//                EntityTemplate.FROZEN_UNDEAD,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                4.0
//            ), // ??????????????????
////                SpawnEntry(CustomEntity.DRAUGR, listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA), 1.0), // ???????????? ??
//            SpawnEntry(
//                EntityTemplate.FROST_SPIDER,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                2.0
//            ), // ???????????????? ????????
//            SpawnEntry(
//                EntityTemplate.STRAY,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                3.0
//            ), // ????????????
//            SpawnEntry(
//                EntityTemplate.SNOW_CREEPER,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0
//            ), // ????????????
//
//            // ????????????????
//            SpawnEntry(
//                EntityTemplate.COW,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0,
//                -2..3
//            ),
//            SpawnEntry(
//                EntityTemplate.SHEEP,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0,
//                -2..3
//            ),
//            SpawnEntry(
//                EntityTemplate.WOLF,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0,
//                -2..4
//            ),
//            SpawnEntry(
//                EntityTemplate.FOX,
//                listOf(BiomeType.FROZEN_RIVER, BiomeType.MOUNTAINS, BiomeType.TAIGA, BiomeType.COLD_TAIGA),
//                1.0,
//                -2..4
//            ),
//
//
//            // MESA
//            // https://www.youtube.com/watch?v=P3naRMrBNR8
//            SpawnEntry(EntityTemplate.STRAY, listOf(BiomeType.MESA), 1.0),
//            SpawnEntry(EntityTemplate.HUSK, listOf(BiomeType.MESA), 1.0),
//            SpawnEntry(EntityTemplate.SPIDER, listOf(BiomeType.MESA), 1.0),
//
//            // ICE_PEAKS ->
//            // ??????????
//            SpawnEntry(EntityTemplate.FROZEN_UNDEAD, listOf(BiomeType.ICE_PEAKS), 1.0), // ??????????????????
//            SpawnEntry(EntityTemplate.ICE_SKELETON, listOf(BiomeType.ICE_PEAKS), 1.0), // ????????????-???????????? (Enchanted)
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.POLAR_BEAR, listOf(BiomeType.ICE_PEAKS), 1.0),
//            SpawnEntry(EntityTemplate.FROZEN_GHOST, listOf(BiomeType.ICE_PEAKS), 1.0),
//
//            // MUSHROOM ->
//            // ??????????
//            SpawnEntry(EntityTemplate.SHROOMBIE, listOf(BiomeType.MUSHROOM), 1.0), // ????????????????????
//            SpawnEntry(EntityTemplate.MOLDY, listOf(BiomeType.MUSHROOM), 1.0),// ??????????????????
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.MOOSHROOM, listOf(), 1.0),
//
//            // JUNGLE
//            // ??????????
//            SpawnEntry(EntityTemplate.JUNGLE_ZOMBIE, listOf(BiomeType.JUNGLE), 1.0, -1..4),// ????????????????????
//            SpawnEntry(EntityTemplate.JUNGLE_SKELETON, listOf(), 1.0, 3..6), // Enchanted Skeleton
//            SpawnEntry(EntityTemplate.FROG, listOf(BiomeType.JUNGLE), 1.0, -3..3), // ??????????????
//            SpawnEntry(EntityTemplate.WOODEN_FROG, listOf(BiomeType.JUNGLE), 1.0, -3..5),// ???????????????? ??????????????
//            SpawnEntry(EntityTemplate.BEETLE, listOf(BiomeType.JUNGLE), 1.0, 0..6), // ???????????????????????? ???????? // ??????
//
//            // ????????????????
//            SpawnEntry(EntityTemplate.PARROT, listOf(BiomeType.JUNGLE), 1.0, -4..4),
//            SpawnEntry(EntityTemplate.OCELOT, listOf(BiomeType.JUNGLE), 1.0, -2..4),
//            SpawnEntry(EntityTemplate.RACCOON, listOf(BiomeType.JUNGLE), 1.0, -2..4),


            // LATE GAME

            SpawnEntry(EntityTemplate.PILLAGER, NOT_IN_WATER, 0.01, 1..3),
            SpawnEntry(EntityTemplate.PILLAGER, NOT_IN_WATER, 0.05, 3..4),
            SpawnEntry(EntityTemplate.PILLAGER, NOT_IN_WATER, 3.0, 4..7),
            SpawnEntry(EntityTemplate.PILLAGER, NOT_IN_WATER, 3.0, 6..7),
            SpawnEntry(EntityTemplate.RAVAGER, NOT_IN_WATER, 0.5, 4..7),
            SpawnEntry(EntityTemplate.RAVAGER, NOT_IN_WATER, 1.5, 6..7),
            SpawnEntry(EntityTemplate.VINDICATOR, NOT_IN_WATER, 1.5, 5..7),
            SpawnEntry(EntityTemplate.EVOKER, NOT_IN_WATER, 1.0, 5..7),
            SpawnEntry(EntityTemplate.ILLUSIONER, NOT_IN_WATER, 0.5, 6..7),
            SpawnEntry(EntityTemplate.VEX, NOT_IN_WATER, 0.75, 6..7)

//                SpawnEntry(EntityTemplate.VEX, listOf(*BiomeType.values()), 0.75, 1..7, listOf(isCave)),
//                SpawnEntry(EntityTemplate.VEX, listOf(*BiomeType.values()), 0.75, 1..7),
//                SpawnEntry(EntityTemplate.VEX, listOf(*BiomeType.values()), 0.75, 1..7),
//                SpawnEntry(EntityTemplate.VEX, listOf(*BiomeType.values()), 0.75, 1..7)

            // CAVES
            //             UNKNOWN ->
            //    {
            //        // ??????????
            //        CustomEntity.ZOMBIE // ??????????
            //        CustomEntity.ZOMBIE // ??????????-????????????
            //        CustomEntity.SKELETON // Enchanted Skeleton
            //        CustomEntity.CAVE_SPIDER // ???????????????? ????????
            //        CustomEntity.BAT // ?????????????????????? ?????????????? ????????
            //        CustomEntity.CREEPER // ???????????????? ????????????
            //
            //
            //        // ????????????????
            //        CustomEntity.SILVERFISH


            //    // UNDERGROUND
            //             UNKNOWN ->
            //    {
            //        // ??????????
            //        CustomEntity.ZOMBIE // ??????????
            //        CustomEntity.SKELETON
            //        CustomEntity.CAVE_SPIDER // ???????????????? ????????
            //        CustomEntity.BAT // ?????????????????????? ?????????????? ????????
            //        CustomEntity.CAVE_CREEPER // ???????????????? ????????????
            //        CustomEntity.PILLAGER_MINER // Pillager-????????????
            //        CustomEntity.ZOMBIE_MINER // ??????????-????????????
            //
            //        CustomEntity.STONE_GOLEM // ???????????????? ??????????
            //        CustomEntity.SHADOW // ????????
            //
            //        // ????????????????
            //        CustomEntity.SILVERFISH
        )

    }
}*/
