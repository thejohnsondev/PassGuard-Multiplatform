import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.crashlytics)
}

val appName = "PassGuard"
val versionNameValue = "1.0.0"

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
            export(project(":core:platform"))
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            // Compose
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // Splashscreen
            implementation(libs.androidx.core.splashscreen)

            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
        }
        commonMain.dependencies {
            api(project(":core:common"))
            api(project(":core:database"))
            api(project(":core:datastore"))
            api(project(":core:model"))
            api(project(":core:network"))
            api(project(":core:platform"))
            api(project(":core:sync"))
            api(project(":core:ui"))
            api(project(":feature:auth:presentation"))
            api(project(":feature:auth:data"))
            api(project(":feature:vault:data"))
            api(project(":feature:vault:domain"))
            api(project(":feature:vault:presentation"))
            api(project(":feature:tools:data"))
            api(project(":feature:tools:domain"))
            api(project(":feature:tools:presentation"))
            api(project(":feature:settings:data"))
            api(project(":feature:settings:domain"))
            api(project(":feature:settings:presentation"))
            api(project(":feature:autofill"))

            // Compose
            implementation(compose.runtime)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.material3.windowsizeclass.multiplatform)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.navigation.compose)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)

            // Haze
            implementation(libs.haze.haze)
            implementation(libs.haze.materials)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs) {
                exclude("org.jetbrains.compose.material")
            }
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.compose.jetbrains.expui.theme)
        }
        val iosMain by creating {
            dependencies {

            }
        }

        /* Example of how to create a source set that depends on multiple source sets
        val jvmAndMacos by creating {
            dependsOn(commonMain.get())
        }

        macosArm64Main.get().dependsOn(jvmAndMacos)
        jvmMain.get().dependsOn(jvmAndMacos)
         */
    }
}

android {
    namespace = "org.thejohnsondev.vault"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.thejohnsondev.vault"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = versionNameValue
        setProperty("archivesBaseName", appName)
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "org.thejohnsondev.vault.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.thejohnsondev.vault"
            packageVersion = "1.0.0"
            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
            modules("jdk.unsupported")
        }
    }
}
