plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    setLibsDirectory()
    applyXplatConnection(":changes-xplat")
}

kpublish {
    createPublication()
}

neoForge {
    runs {
        create("client") {
            client()
            systemProperty("mixin.debug.export", "true")
        }
    }
}
