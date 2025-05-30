import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
        androidMain.dependencies {
            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // SQLDelight
            implementation(libs.sqldelight.android)
        }
        commonMain.dependencies {
            api(project(":core:common"))
            api(project(":core:model"))

            implementation(libs.ktor.serialization.kotlinx.json)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)

            // Arrow Either
            implementation(libs.arrow.core)

            // SQLDelight
            implementation(libs.sqldelight.coroutines)
        }
        desktopMain.dependencies {
            implementation(libs.sqldelight.jvm)
        }
        nativeMain {
            dependencies {
                implementation(libs.sqldelight.native)
            }
        }
    }
}

sqldelight {
    databases {
        create("VaultDatabase") {
            packageName.set("org.thejohnsondev.vault.database")
        }
    }
}

android {
    namespace = "org.thejohnsondev.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}