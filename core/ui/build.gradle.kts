import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
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
        val desktopTest by getting
        val desktopMain by getting
        androidMain.dependencies {
            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // Compose
            implementation(compose.components.uiToolingPreview)
        }
        commonMain.dependencies {
            implementation(libs.ktor.serialization.kotlinx.json)

            api(project(":core:model"))
            api(project(":core:common"))

            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(libs.material3.windowsizeclass.multiplatform)
            implementation(libs.navigation.compose)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)

            // Haze
            implementation(libs.haze.haze)
            implementation(libs.haze.materials)

            // Landscapist Coil3
            implementation(libs.landscapist.coil3)
        }
        commonTest.dependencies {
            // Testing
            implementation(kotlin("test"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        desktopTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "vaultmultiplatform.core.ui.generated.resources"
    generateResClass = always
}

android {
    namespace = "org.thejohnsondev.ui"
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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

    // Preview
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Tests
    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    debugImplementation(libs.androidx.ui.test.manifest)
}
