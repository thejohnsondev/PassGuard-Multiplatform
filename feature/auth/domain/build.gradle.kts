import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildkonfig)
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
        commonMain.dependencies {
            api(project(":core:model"))
            api(project(":core:common"))
            implementation(project(":feature:auth:data"))

            implementation(libs.ktor.serialization.kotlinx.json)

            // Koin
            api(libs.koin.core)

            // Arrow Either
            implementation(libs.arrow.core)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.arrow.core)
            implementation(libs.mockk)
        }
    }
}

android {
    namespace = "org.thejohnsondev.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

buildkonfig {
    packageName = "org.thejohnsondev.domain"

    val localPropsFile = rootProject.file("local.properties")
    val localProperties = Properties()
    if (localPropsFile.exists()) {
        runCatching {
            localProperties.load(localPropsFile.inputStream())
        }.getOrElse {
            it.printStackTrace()
        }
    }
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "AUTH_SECRET_KEY",
            localProperties["auth_secret_key"]?.toString() ?: ""
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "AUTH_SECRET_IV",
            localProperties["auth_secret_iv"]?.toString() ?: ""
        )
    }

}