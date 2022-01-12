pluginManagement {
  repositories {
    gradlePluginPortal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven (url = "https://mvn.lumine.io/repository/maven-public")
    maven ( url = "https://nexus.recraft.pro/repository/maven-public/")
    maven (url = "https://oss.sonatype.org/content/groups/public/")
  }
}

rootProject.name = "danger-plugin"
