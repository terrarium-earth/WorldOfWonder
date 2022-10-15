plugins {
    id("dev.architectury.loom") version "0.12.0-SNAPSHOT"
}

loom {
    accessWidenerPath.set(file("src/main/resources/worldofwonder.accessWidener"))

    forge {
        convertAccessWideners.set(true)
    }
}

dependencies {
    val minecraftVersion: String by project
    val forgeVersion: String by project

    minecraft("::1.16.5")
    mappings(loom.officialMojangMappings())
    forge(group = "net.minecraftforge", name = "forge", version = "${minecraftVersion}-${forgeVersion}")
}
