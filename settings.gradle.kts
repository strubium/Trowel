rootProject.name = "Trowel"
pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.teamvoided.org/releases")
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}
