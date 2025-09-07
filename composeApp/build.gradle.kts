import org.jetbrains.compose.desktop.application.dsl.TargetFormat
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
val versionNameValue = libs.versions.versionName.get()
val versionCodeValue = libs.versions.versionCode.get().toInt()

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
            export(project(":core:platform"))
            export(project(":core:analytics"))
        }
    }
    
    sourceSets {
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
            api(project(":core:analytics"))
            api(project(":core:common"))
            api(project(":core:database"))
            api(project(":core:datastore"))
            api(project(":core:localization"))
            api(project(":core:model"))
            api(project(":core:network"))
            api(project(":core:platform"))
            api(project(":core:sync"))
            api(project(":core:ui"))
            api(project(":core:biometric"))
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
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel)
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
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.compose.jetbrains.expui.theme)
            }
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.junit)
            implementation(libs.androidx.ui.test.junit4)
            implementation(libs.espresso.core)
            implementation(libs.androidx.ui.test.junit4)
        }
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
        versionCode = versionCodeValue
        versionName = versionNameValue
        setProperty("archivesBaseName", appName)
        testInstrumentationRunner = "com.thejohnsondev.vault.utils.TestRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            manifestPlaceholders["appName"] = appName
            signingConfig = signingConfigs.getByName("debug")
            postprocessing {
                isObfuscate = false
                isOptimizeCode = true
                isRemoveUnusedCode = true
                isRemoveUnusedResources = true
                proguardFile("proguard-rules.pro")
            }
        }
        getByName("debug") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            manifestPlaceholders["appName"] = "$appName Dev"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    lint {
        disable.add("NullSafeMutableLiveData")
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        androidTestImplementation(libs.runner)
        androidTestImplementation(libs.androidx.ui.test.junit4)
    }
}

compose.desktop {
    application {
        mainClass = "org.thejohnsondev.vault.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PassGuard"
            packageVersion = versionNameValue
            jvmArgs(
                "-Dapple.awt.application.appearance=system"
            )
            modules("jdk.unsupported")
            modules("java.sql")
            macOS {
                iconFile.set(project.file("icon/PassGuard-icon-iOS-Dark-1024x1024@1x.icns"))
            }
        }
    }
}