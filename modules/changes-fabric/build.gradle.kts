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

loom {
    runs {
        named("client") {
            ideConfigGenerated(true)
            vmArgs("-Dmixin.debug.export=true")
        }
    }
}
