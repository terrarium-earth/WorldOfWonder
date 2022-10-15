plugins {
    id("dev.architectury.loom") version "0.12.0-SNAPSHOT"
}

loom {
    accessWidenerPath.set(file("src/main/resources/worldofwonder.accessWidener"))

    silentMojangMappingsLicense()

    forge {
        convertAccessWideners.set(true)
    }
}

repositories {
    maven(url = "https://maven.parchmentmc.org/")
}

dependencies {
    val minecraftVersion: String by project
    val forgeVersion: String by project

    minecraft("::${minecraftVersion}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        val parchmentVersion: String by project

        officialMojangMappings()
        parchment(create(group = "org.parchmentmc.data", name = "parchment-$minecraftVersion", version = parchmentVersion, ext = "zip"))
    })

    forge(group = "net.minecraftforge", name = "forge", version = "${minecraftVersion}-${forgeVersion}")
}
