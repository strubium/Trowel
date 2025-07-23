plugins {
    id("fabric-loom") version "1.6.11"
}

group = "com.theendercore"
version = "1.0.0"
base.archivesName.set("trowel")
description = "A tool for placing random blocks from your hotbar."

val modid = "trowel"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") // Fabric Maven
}

dependencies {
    minecraft("com.mojang:minecraft:1.16.5")
    mappings("net.fabricmc:yarn:1.16.5+build.9:v2")
    modImplementation("net.fabricmc:fabric-loader:0.11.6")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.42.0+1.16")
}

loom {
    runs {
        named("client") {
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8)) // Java 8 is standard for 1.16.5
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    // No `options.release.set(21)` since we're targeting Java 8
}
