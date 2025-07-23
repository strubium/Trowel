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
    minecraft("com.mojang:minecraft:1.14.4")
    mappings("net.fabricmc:yarn:1.14.4+build.18:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.28.5+1.14")
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
