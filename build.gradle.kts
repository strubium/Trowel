plugins {
    id("fabric-loom") version "1.6-SNAPSHOT" // latest Loom that supports 1.20.1
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
    minecraft("com.mojang:minecraft:1.20.1")
    mappings("net.fabricmc:yarn:1.20.1+build.10:v2")
    modImplementation("net.fabricmc:fabric-loader:0.14.22")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.85.0+1.20.1")
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
        options.release.set(17)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        withSourcesJar()
    }
}
