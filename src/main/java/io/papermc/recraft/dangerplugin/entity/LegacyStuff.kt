//package io.recraft.danger.entity
//
//import io.recraft.entities.BehaviorTarget
//import io.recraft.entities.CreatureType
//import io.recraft.entities.EntityBehavior
//import io.recraft.entities.EntityTemplate
//import org.bukkit.entity.EntityType
//
//object LegacyStuff {
//
//    val DO_NOT_TOUCH_MOB_TYPES = listOf(
//        EntityType.SQUID,
//        EntityType.TROPICAL_FISH,
//        EntityType.PUFFERFISH,
//        EntityType.SALMON,
//        EntityType.COD
//    )
//
//    val AVOID_MONSTERS = EntityBehavior(
//        avoid = listOf(
//            BehaviorTarget(classes = listOf("MONSTERS"))
//        )
//    )
//
//
//    val ATTACK_MONSTERS = EntityBehavior(
//        attack = listOf(
//            BehaviorTarget(classes = listOf("MONSTERS"))
//        )
//    )
//
//
//    val ATTACK_PILLAGERS_AND_ANIMALS = EntityBehavior(
//        attack = listOf(
//            BehaviorTarget(classes = listOf("PILLAGERS", "ANIMALS"))
//        )
//    )
//
//    val ATTACK_MOBS_AND_ANIMALS = EntityBehavior(
//        attack = listOf(
//            BehaviorTarget(classes = listOf("PILLAGERS", "ANIMALS"))
//        )
//    )
//
//    // Entities
//    // Animals
//    val PIG = EntityTemplate(
//        "pig", EntityType.PIG, "Свинья", CreatureType.ANIMAL, AVOID_MONSTERS
//    )
//    val COW = EntityTemplate("cow", EntityType.COW, "Корова", CreatureType.ANIMAL, AVOID_MONSTERS)
//
//    val SHEEP = EntityTemplate("sheep", EntityType.SHEEP, "Овца", CreatureType.ANIMAL, AVOID_MONSTERS)
//
//    val OCELOT = EntityTemplate("ocelot", EntityType.OCELOT, "Оцелот", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val POLAR_BEAR = EntityTemplate("polar_bear", EntityType.WOLF, "Белый медведь", CreatureType.ANIMAL)
//    val BAT = EntityTemplate("bat", EntityType.BAT, "Летучая мышь", CreatureType.ANIMAL)
//    val PARROT =
//        EntityTemplate("parrot", EntityType.PARROT, "Попугай", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val RABBIT = EntityTemplate("rabbit", EntityType.RABBIT, "Кролик", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val WOLF = EntityTemplate("wolf", EntityType.WOLF, "Волк", CreatureType.ANIMAL)
//    val FOX = EntityTemplate("fox", EntityType.FOX, "Лисица", CreatureType.ANIMAL)
//    val MOOSHROOM = EntityTemplate("mushroom_cow", EntityType.MUSHROOM_COW, "Грибная корова", CreatureType.ANIMAL)
//
//    // Custom
//    val LYNX = EntityTemplate("lynx", EntityType.OCELOT, "Рысь", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val FROG = EntityTemplate("frog", EntityType.RABBIT, "Лягушка", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val LLAMA = EntityTemplate("llama", EntityType.LLAMA, "Лама", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val CAMEL = EntityTemplate("camel", EntityType.LLAMA, "Верблюд", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val ZEBRA = EntityTemplate("zebra", EntityType.HORSE, "Зебра", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val HYENA = EntityTemplate("hyena", EntityType.WOLF, "Гиена", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val BROWN_BEAR = EntityTemplate("brown_bear", EntityType.POLAR_BEAR, "Бурый медведь", CreatureType.ANIMAL)
//    val RACCOON = EntityTemplate("raccoon", EntityType.FOX, "Енот", CreatureType.ANIMAL, AVOID_MONSTERS)
//    val DEER = EntityTemplate("deer", EntityType.MULE, "Олень", CreatureType.ANIMAL)
//    val RAT = EntityTemplate("rat", EntityType.SILVERFISH, "Крыса", CreatureType.ANIMAL)
//
//    val WOODEN_FROG = EntityTemplate("wooden_frog", EntityType.RABBIT, "Древесная лягушка", CreatureType.ANIMAL)
//
//    // Hostile
//    val CREEPER = EntityTemplate("creeper", EntityType.CREEPER, "Крипер", CreatureType.MONSTER)
//    val SKELETON = EntityTemplate(
//        "skeleton",
//        EntityType.SKELETON,
//        "Скелет",
//        CreatureType.MONSTER,
//        ATTACK_PILLAGERS_AND_ANIMALS
//    )
//    val ZOMBIE =
//        EntityTemplate("zombie", EntityType.ZOMBIE, "Зомби", CreatureType.MONSTER, ATTACK_PILLAGERS_AND_ANIMALS)
//
//    val DROWNED =
//        EntityTemplate("drowned", EntityType.DROWNED, "Утопец", CreatureType.MONSTER, ATTACK_PILLAGERS_AND_ANIMALS)
//    val HUSK = EntityTemplate("husk", EntityType.HUSK, "Зомби", CreatureType.MONSTER, ATTACK_PILLAGERS_AND_ANIMALS)
//    val STRAY =
//        EntityTemplate("stray", EntityType.STRAY, "Зимогор", CreatureType.MONSTER, ATTACK_PILLAGERS_AND_ANIMALS)
//    val SPIDER = EntityTemplate("spider", EntityType.SPIDER, "Паук", CreatureType.MONSTER)
//    val CAVE_SPIDER = EntityTemplate("cave_spider", EntityType.CAVE_SPIDER, "Пещерный-паук", CreatureType.MONSTER)
//    val BEETLE = EntityTemplate("beetle", EntityType.SPIDER, "Жук", CreatureType.MONSTER)
//    val SLIME = EntityTemplate("slime", EntityType.SLIME, "Слизень", CreatureType.ANIMAL)
//
//    // Custom
//    // Swamp
//    val ROTFIEND = EntityTemplate("rotfiend", EntityType.HUSK, "Гнилец", CreatureType.MONSTER)
//    val SWAMP_SPIDER = EntityTemplate("swamp_spider", EntityType.SPIDER, "Болотный паук", CreatureType.MONSTER)
//    val SWAMP_WITCH = EntityTemplate("swamp_witch", EntityType.WITCH, "Болотная ведьма", CreatureType.MONSTER)
//
//    // Desert
//    val DESERT_SPIDER =
//        EntityTemplate("desert_spider", EntityType.CAVE_SPIDER, "Пустынный паук", CreatureType.MONSTER)
//    val MUMMY = EntityTemplate("mummy", EntityType.ZOMBIE, "Мумия", CreatureType.MONSTER)
//    val DESERT_CULTIST = EntityTemplate("desert_skeleton", EntityType.STRAY, "Культист", CreatureType.MONSTER)
//    val DESERT_CREEPER =
//        EntityTemplate("desert_creeper", EntityType.CREEPER, "Пустынный крипер", CreatureType.MONSTER)
//    val SCORPIO = EntityTemplate("scorpio", EntityType.SPIDER, "Скорпион", CreatureType.MONSTER)
//
//    //CAVE
//    val ZOMBIE_MINER = EntityTemplate("zombie_miner", EntityType.STRAY, "Зомби-шахтер", CreatureType.MONSTER)
//    val PILLAGER_MINER =
//        EntityTemplate("pillager_miner", EntityType.PILLAGER, "Разбойник-шахтер", CreatureType.MONSTER)
//    val SHADOW = EntityTemplate("shadow", EntityType.VEX, "shadow", CreatureType.MONSTER)
//    val STONE_GOLEM = EntityTemplate("stone_golem", EntityType.IRON_GOLEM, "Каменный голем", CreatureType.MONSTER)
//    val CAVE_CREEPER = EntityTemplate("cave_creeper", EntityType.CREEPER, "Пещерный крипер", CreatureType.MONSTER)
//    val CAVE_SLIME = EntityTemplate("cave_slime", EntityType.SLIME, "Пещерный слизень", CreatureType.ANIMAL)
//
//    // ICE
//    val ICE_SKELETON = EntityTemplate("ice_skeleton", EntityType.STRAY, "Зачарованный скелет", CreatureType.MONSTER)
//    val FROZEN_GHOST = EntityTemplate("frozen_ghost", EntityType.BEE, "Ледяное привидение", CreatureType.MONSTER)
//
//    // COLD
//    val FROZEN_UNDEAD = EntityTemplate("frozen_zombie", EntityType.ZOMBIE, "Замерзший", CreatureType.MONSTER)
//    val DRAUGR = EntityTemplate("draugr", EntityType.ZOMBIE, "Драугр", CreatureType.MONSTER)
//    val SNOW_CREEPER = EntityTemplate("snow_creeper", EntityType.CREEPER, "Снежный крипер", CreatureType.MONSTER)
//    val FROST_SPIDER = EntityTemplate("frostbite_spider", EntityType.SPIDER, "Морозный паук", CreatureType.MONSTER)
//
//    // Mushroom
//    val MOLDY = EntityTemplate("moldy", EntityType.CAVE_SPIDER, "Плесневик", CreatureType.MONSTER)
//    val SHROOMBIE = EntityTemplate("shroombie", EntityType.ZOMBIE, "Пораженный", CreatureType.MONSTER)
//
//    // Jungle
//    val JUNGLE_SKELETON =
//        EntityTemplate("jungle_skeleton", EntityType.STRAY, "Зачарованный скелет", CreatureType.MONSTER)
//    val JUNGLE_ZOMBIE = EntityTemplate("jungle_zombie", EntityType.STRAY, "Восставший", CreatureType.MONSTER)
//
//    // PILLAGERS
//    val PILLAGER =
//        EntityTemplate("pillager", EntityType.PILLAGER, "Разбойник", CreatureType.MONSTER, ATTACK_MONSTERS)
//    val VINDICATOR =
//        EntityTemplate("vindicator", EntityType.VINDICATOR, "Поборник", CreatureType.MONSTER, ATTACK_MONSTERS)
//    val VEX = EntityTemplate("vex", EntityType.VEX, "Досаждатель", CreatureType.MONSTER, ATTACK_MONSTERS)
//    val EVOKER = EntityTemplate("evoker", EntityType.EVOKER, "Призыватель", CreatureType.MONSTER, ATTACK_MONSTERS)
//    val ILLUSIONER =
//        EntityTemplate("illusioner", EntityType.ILLUSIONER, "Иллюзор", CreatureType.MONSTER, ATTACK_MONSTERS)
//    val RAVAGER = EntityTemplate("ravager", EntityType.RAVAGER, "Разоритель", CreatureType.MONSTER, ATTACK_MONSTERS)
//
//}
