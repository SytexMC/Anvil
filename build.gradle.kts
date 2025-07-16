allprojects {
    group = "me.sytex"
    version = "1.1.0"

    ext { set("javaToolchainVersion", 17) }

    tasks.withType<Jar> {
        archiveBaseName.set(rootProject.name)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
