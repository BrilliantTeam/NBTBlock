import io.izzel.taboolib.gradle.*


plugins {
    java
    id("io.izzel.taboolib") version "2.0.11"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

taboolib {
    env {
        install(UNIVERSAL, BUKKIT_ALL, NMS_UTIL, METRICS)
    }
    description {
        name = "NBTBlock"
        desc("Kepp your block NBT when it has been placed")
        contributors {
            name("L1An")
        }
    }
    version { taboolib = "6.1.1-beta17" }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.withType<Jar> {
    //destinationDirectory.set(file("/Users/yuxin/minecraft/servers/1.20.4Test/plugins"))
    destinationDirectory.set(file("$projectDir/build-jar"))
}