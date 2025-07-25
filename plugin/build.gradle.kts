plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(project.ext.get("javaToolchainVersion") as Int)) }

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(project.ext.get("javaToolchainVersion") as Int)
    }

    javadoc { options.encoding = Charsets.UTF_8.name() }
    processResources { filteringCharset = Charsets.UTF_8.name() }

    runServer {
        minecraftVersion("1.19.4")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

paper {
    main = "me.sytex.anvil.Anvil"

    apiVersion = "1.19"

    foliaSupported = true

    name = "Anvil"
    description = "Repair anvils with iron ingots or damage them with obsidian, via crafting or by shift + right-clicking them."
    version = project.version as String

    authors = listOf("Sytex")
    contributors = listOf()
}