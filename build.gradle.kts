plugins {
    java

    id("fabric-loom") version "1.0.+"
    id("io.github.juuxel.loom-quiltflower") version "1.8.+"

    id("com.modrinth.minotaur") version "2.5.+"
    id("me.hypherionmc.cursegradle") version "2.+"
    id("com.github.breadmoirai.github-release") version "2.+"
    id("io.github.p03w.machete") version "1.+"
    `maven-publish`
}

group = "dev.isxander"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://maven.isxander.dev/releases")
    maven("https://maven.isxander.dev/snapshots")
    maven("https://maven.flashyreese.me/releases")
    maven("https://maven.flashyreese.me/snapshots")
    maven("https://maven.shedaniel.me")
    maven("https://maven.terraformersmc.com")
    maven("https://jitpack.io")
    maven("https://maven.gegy.dev")
    maven("https://api.modrinth.com/maven") {
        content {
            includeGroup("maven.modrinth")
        }
    }
}

val minecraftVersion: String by project
val fabricLoaderVersion: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.+:v2")
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

    modImplementation("dev.isxander:yet-another-config-lib:2.2.0")
    modImplementation("maven.modrinth:sodium:mc1.19.3-0.4.6")
    modImplementation("com.terraformersmc:modmenu:5.0.2")

    // sodium extra better options compat
    modImplementation("maven.modrinth:sodium-extra:mc1.19.3-0.4.15")
    // moreculling category placement
    modImplementation("maven.modrinth:moreculling:v0.12.3")
    // iris category placement
    modImplementation("maven.modrinth:iris:1.4.6+1.19.3")
    // entityviewdistance button option compat
    modImplementation("maven.modrinth:entity-view-distance:1.1.0+1.19.3")

    modRuntimeOnly("me.shedaniel.cloth:cloth-config-fabric:9.0.+")
    modRuntimeOnly("dev.lambdaurora:spruceui:4.1.0+1.19.3")

    implementation(include(annotationProcessor("com.github.llamalad7:mixinextras:0.1.1")!!)!!)
}

tasks {
    processResources {
        val modId: String by project
        val modName: String by project
        val modDescription: String by project
        val githubProject: String by project

        inputs.property("id", modId)
        inputs.property("group", project.group)
        inputs.property("name", modName)
        inputs.property("description", modDescription)
        inputs.property("version", project.version)
        inputs.property("github", githubProject)

        filesMatching(listOf("fabric.mod.json", "quilt.mod.json")) {
            expand(
                "id" to modId,
                "group" to project.group,
                "name" to modName,
                "description" to modDescription,
                "version" to project.version,
                "github" to githubProject,
            )
        }
    }
    
    remapJar {
        archiveClassifier.set("fabric-$minecraftVersion")   
    }
    
    remapSourcesJar {
        archiveClassifier.set("fabric-$minecraftVersion-sources")   
    }

    register("releaseMod") {
        group = "mod"

        dependsOn("modrinth")
        dependsOn("modrinthSyncBody")
        dependsOn("curseforge")
        dependsOn("publish")
        dependsOn("githubRelease")
    }
}

java {
    withSourcesJar()   
}

val changelogText = file("changelogs/${project.version}.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."

val modrinthId: String by project
if (modrinthId.isNotEmpty()) {
    modrinth {
        token.set(findProperty("modrinth.token")?.toString())
        projectId.set(modrinthId)
        versionNumber.set("${project.version}")
        versionType.set("release")
        uploadFile.set(tasks["remapJar"])
        gameVersions.set(listOf("1.19", "1.19.1", "1.19.2"))
        loaders.set(listOf("fabric", "quilt"))
        changelog.set(changelogText)
        syncBodyFrom.set(file(".github/README.md").readText())

        dependencies {
            required.project("sodium")
            required.project("yacl")
            incompatible.project("reeses-sodium-options")
        }
    }
}

val curseforgeId: String by project
if (hasProperty("curseforge.token") && curseforgeId.isNotEmpty()) {
    curseforge {
        apiKey = findProperty("curseforge.token")
        project(closureOf<me.hypherionmc.cursegradle.CurseProject> {
            mainArtifact(tasks["remapJar"], closureOf<me.hypherionmc.cursegradle.CurseArtifact> {
                displayName = "${project.version}"
            })

            id = curseforgeId
            releaseType = "release"
            addGameVersion("1.19")
            addGameVersion("1.19.1")
            addGameVersion("1.19.2")
            addGameVersion("Fabric")
            addGameVersion("Java 17")

            changelog = changelogText
            changelogType = "markdown"

            relations(closureOf<me.hypherionmc.cursegradle.CurseRelation> {
                requiredDependency("sodium")
                requiredDependency("yacl")
                incompatible("reeses-sodium-options")
            })
        })

        options(closureOf<me.hypherionmc.cursegradle.Options> {
            forgeGradleIntegration = false
        })
    }
}

githubRelease {
    token(findProperty("github.token")?.toString())

    val githubProject: String by project
    val split = githubProject.split("/")
    owner(split[0])
    repo(split[1])
    tagName("${project.version}")
    targetCommitish("1.19")
    body(changelogText)
    releaseAssets(tasks["remapJar"].outputs.files)
}

publishing {
    publications {
        create<MavenPublication>("mod") {
            groupId = "dev.isxander"
            artifactId = "xanders-sodium-options"

            from(components["java"])
        }
    }

    repositories {
        if (hasProperty("xander-repo.username") && hasProperty("xander-repo.password")) {
            maven(url = "https://maven.isxander.dev/releases") {
                credentials {
                    username = property("xander-repo.username")?.toString()
                    password = property("xander-repo.password")?.toString()
                }
            }
        } else {
            println("Xander Maven credentials not satisfied.")   
        }
    }
}
