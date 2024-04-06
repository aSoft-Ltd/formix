import org.jetbrains.kotlin.gradle.targets.js.KotlinWasmTargetType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "The models of the formix library"

kotlin {
    jvm {
        withJava()
        library()
    }

    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.WASM) wasmWasi { library() }
    val iosTargets = if (Targeting.OSX) iosTargets() else listOf()
    val tvOsTargets = if (Targeting.OSX) tvOsTargets() else listOf()
    val macOsTargets = if (Targeting.OSX) macOsTargets() else listOf()
    val watchOsTargets = if (Targeting.OSX) watchOsTargets() else listOf()
    val osxTargets = iosTargets + tvOsTargets + macOsTargets + watchOsTargets
    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    val nativeTargets = osxTargets + ndkTargets + linuxTargets + mingwTargets

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
            }
        }

        if (Targeting.JVM) {
            val jvmMain by getting {
                dependencies {
                    api(kotlin("test-junit5"))
                }
            }
        }

        val nonJvmMain by creating {
            dependsOn(commonMain)
        }

        val nativeMain by creating {
            dependsOn(nonJvmMain)
        }

        if(Targeting.OSX) {
            val osxMain by creating {
                dependsOn(nativeMain)
            }

            val watchOsMain by creating {
                dependsOn(osxMain)
            }

            nativeTargets.forEach {
                val main by it.compilations.getting {}
                main.defaultSourceSet {
                    dependsOn(nativeMain)
                    if (it in watchOsTargets) dependsOn(watchOsMain)
                    if (it in osxTargets) dependsOn(osxMain)
                }
            }
        }


        if (Targeting.JS) {
            val jsMain by getting {
                dependsOn(nonJvmMain)
            }
        }

        if (Targeting.WASM) {
            val wasmJsMain by getting {
                dependsOn(nonJvmMain)
            }

            val wasmWasiMain by getting {
                dependsOn(nonJvmMain)
            }
        }

        nativeTargets.forEach {
            val main by it.compilations.getting {}
            main.defaultSourceSet {
                dependsOn(nativeMain)
            }
        }
    }
}

rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = npm.versions.node.version.get()
    nodeDownloadBaseUrl = npm.versions.node.url.get()
}

rootProject.tasks.withType<KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}

tasks.named("wasmJsTestTestDevelopmentExecutableCompileSync").configure {
    mustRunAfter(tasks.named("jsBrowserTest"))
    mustRunAfter(tasks.named("jsNodeTest"))
}