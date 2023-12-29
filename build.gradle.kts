import java.io.FileOutputStream

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("com.github.johnrengelman.shadow") version ("8.1.1")
    id("maven-publish")
}

group = "com.laudynetwork.gameengine"
version = "1.0-SNAPSHOT"
description = "LaudyNetwork Game Engine"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
    maven("https://eldonexus.de/repository/maven-proxies/")
    maven("https://repo.ranull.com/maven/external")
    maven {
        url = uri("https://repo.laudynetwork.com/repository/maven")
        authentication {
            create<BasicAuthentication>("basic")
        }
        credentials {
            username = System.getenv("NEXUS_USER")
            password = System.getenv("NEXUS_PWD")
        }
    }
}

dependencies {

    // paper

    paperweight.paperDevBundle("1.20-R0.1-SNAPSHOT")

    // LN provided api

    compileOnly("com.laudynetwork:networkutils:latest") {
        exclude(group = "eu.thesimplecloud.simplecloud", module = "simplecloud-api")
        exclude(group = "com.laudynetwork", module = "database")
        exclude(group = "org.mongodb", module = "mongodb-driver-sync")
    }

    // 3'trd layer api

    compileOnly("org.mongodb:mongodb-driver-sync:4.11.1")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    shadowJar {
        dependencies {
            exclude(dependency("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT"))
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("dist/GameEngine.jar"))
    }
}

tasks.register("translations") {
    downloadFile(System.getenv("TOLGEE_TOKEN_PLUGIN"), "own")
    downloadFile(System.getenv("TOLGEE_TOKEN_GENERAL"), "plugins")
}

fun downloadFile(token: String, dir: String) {
    downloadLink(token).forEach {
        downloadFromServer(it.key, it.value + ".json", dir)
    }
}

fun downloadFromServer(url: String, fileName: String, dir: String) {
    file("${projectDir}/src/main/resources/translations/${dir}").mkdirs()
    val f = file("${projectDir}/src/main/resources/translations/${dir}/${fileName}")
    uri(url).toURL().openStream().use {
        it.copyTo(
                FileOutputStream(f)
        )
    }
}

fun downloadLink(token: String): Map<String, String> {
    val map = HashMap<String, String>()
    val params = "format=JSON&zip=false&structureDelimiter"
    map["https://tolgee.laudynetwork.com/v2/projects/export?languages=en&$params&ak=$token"] = "en"
    map["https://tolgee.laudynetwork.com/v2/projects/export?languages=de&$params&ak=$token"] = "de"
    return map
}

// publish to nexus
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.laudynetwork"
            artifactId = "gameengine"
            version = "latest"

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://repo.laudynetwork.com/repository/maven")
            credentials {
                username = System.getenv("NEXUS_USER")
                password = System.getenv("NEXUS_PWD")
            }
        }
    }
}