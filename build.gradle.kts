plugins {
    id("fabric-loom") version "1.6.11" // or latest
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
    minecraft("com.mojang:minecraft:1.19.2")
    mappings("net.fabricmc:yarn:1.19.2+build.28:v2")
    modImplementation("net.fabricmc:fabric-loader:0.15.10")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.77.0+1.19.2")
}

loom {
    runs {
        named("client") {
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withSourcesJar()
    }
}
