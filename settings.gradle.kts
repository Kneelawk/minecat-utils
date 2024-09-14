pluginManagement {
    repositories {
        maven("https://maven.quiltmc.org/repository/release") {
            name = "Quilt"
        }
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://maven.architectury.dev/") {
            name = "Architectury"
        }
        maven("https://maven.neoforged.net/releases/") {
            name = "NeoForged"
        }
        maven("https://maven.kneelawk.com/releases/") {
            name = "Kneelawk"
        }
        gradlePluginPortal()
    }
    plugins {
        val loom_version: String by settings
        id("fabric-loom") version loom_version
        val moddev_version: String by settings
        id("net.neoforged.moddev") version moddev_version
        val remapcheck_version: String by settings
        id("com.kneelawk.remapcheck") version remapcheck_version
        val versioning_version: String by settings
        id("com.kneelawk.versioning") version versioning_version
        val kpublish_version: String by settings
        id("com.kneelawk.kpublish") version kpublish_version
        val submodule_version: String by settings
        id("com.kneelawk.submodule") version submodule_version
    }
}

rootProject.name = "minecat-utils"

fun module(enabled: Boolean, name: String) {
    if (!enabled) return
    include(name)
    project(":$name").projectDir = File(rootDir, "modules/${name.replace(':', '/')}")
}

fun module(name: String, vararg submodules: Pair<Boolean, String>) {
    include(name)
    project(":$name").projectDir = File(rootDir, "modules/$name")

    for ((enabled, submodule) in submodules) {
        if (!enabled) continue
        include("$name:$submodule")
        project(":$name:$submodule").projectDir = File(rootDir, "modules/$name/${submodule.replace(':', '/')}")
    }
}

val xplat = true
val mojmap = true
val fabric = true
val neoforge = true

module(xplat, "fixes-xplat")
module(fabric, "fixes-fabric")
module(neoforge, "fixes-neoforge")
