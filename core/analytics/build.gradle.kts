import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinCocoapods)
}

kotlin {
    androidTarget {
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

    cocoapods {
        summary = "Analytics module"
        version = "1.0"
        ios.deploymentTarget = "13.0"

        framework {
            baseName = "ComposeApp"
            isStatic = true
        }

        pod("PostHog") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.posthog.android)
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.posthog.java)
            }
        }
        commonMain.dependencies {
            implementation(libs.ktor.serialization.kotlinx.json)

            // Koin
            api(libs.koin.core)
        }
    }
}

android {
    namespace = "org.thejohnsondev.analytics"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        disable.add("NullSafeMutableLiveData")
    }
}

dependencies {
    androidTestImplementation(libs.runner)
}