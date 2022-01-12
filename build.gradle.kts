import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.3.2"
  id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
  id("net.minecrell.plugin-yml.bukkit") version "0.5.1" // Generates plugin.yml

  // Shades and relocates dependencies into our plugin jar. See https://imperceptiblethoughts.com/shadow/introduction/
  id("com.github.johnrengelman.shadow") version "7.1.0"
  kotlin("jvm") version "1.6.10"
}

group = "io.papermc.paperweight"
version = "1.0.0-SNAPSHOT"
description = "Danger plugin for ReCraft"

java {
  // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
  paperDevBundle("1.18-R0.1-SNAPSHOT")


  implementation("cloud.commandframework", "cloud-paper", "1.6.0")
  //compileOnly ("io.lumine.xikage:MythicMobs:4.9.1")
  implementation(kotlin("stdlib-jdk8"))

  implementation("io.recraft","core", "2.0.1-SNAPSHOT")
  implementation("io.lettuce","lettuce-core", "6.0.0.M1")

}

tasks {
  // Configure reobfJar to run when invoking the build task
  build {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

    // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
    // See https://openjdk.java.net/jeps/247 for more information.
    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
  }

  /*
  reobfJar {
    // This is an example of how you might change the output location for reobfJar. It's recommended not to do this
    // for a variety of reasons, however it's asked frequently enough that an example of how to do it is included here.
    outputJar.set(layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar"))
  }
   */

  shadowJar {
    // helper function to relocate a package into our package
    fun reloc(pkg: String) = relocate(pkg, "io.papermc.recraft.dangerplugin.$pkg")

    // relocate cloud and it's transitive dependencies
    reloc("cloud.commandframework")
    reloc("io.leangen.geantyref")
  }

  shadowJar {
    // helper function to relocate a package into our package
    fun reloc(pkg: String) = relocate(pkg, "io.papermc.recraft.dangerplugin.$pkg")

    // relocate cloud and it's transitive dependencies
    reloc("cloud.commandframework")
    reloc("io.leangen.geantyref")
  }
}

// Configure plugin.yml generation
bukkit {
  load = BukkitPluginDescription.PluginLoadOrder.STARTUP
  main = "io.papermc.recraft.dangerplugin.DangerPlugin"
  authors = listOf("Author")
}
repositories {
  mavenCentral()
  maven (url = "https://mvn.lumine.io/repository/maven-public")
  maven (url = "https://nexus.recraft.pro/repository/maven-public/")
  maven (url = "https://oss.sonatype.org/content/groups/public/")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "17"
}
