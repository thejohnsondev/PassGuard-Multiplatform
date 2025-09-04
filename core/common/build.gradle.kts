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
            implementation(project(":core:model"))

            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.lifecycle.viewmodel)

            // Arrow Either
            implementation(libs.arrow.core)

            // Logs
            implementation(libs.napier)

            // DateTime
            implementation(libs.kotlinx.datetime)

            // Koin
            api(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.ktx)
            implementation(libs.fragment.ktx)

            // Biometric
            implementation(libs.androidx.biometric)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "org.thejohnsondev.common"
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

enum class AppType {
    REAL,
    DEMO
}

buildkonfig {
    packageName = "org.thejohnsondev.common"

    val secretsPropsFile = rootProject.file("secrets.properties")
    val secretsProperties = Properties()
    if (secretsPropsFile.exists()) {
        runCatching {
            secretsProperties.load(secretsPropsFile.inputStream())
        }.getOrElse {
            it.printStackTrace()
        }
    }
    val appConfigPropsFile = rootProject.file("appConfig.properties")
    val appConfigProperties = Properties()
    if (appConfigPropsFile.exists()) {
        runCatching {
            appConfigProperties.load(appConfigPropsFile.inputStream())
        }.getOrElse {
            it.printStackTrace()
        }
    }
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "FIREBASE_API_KEY",
            secretsProperties["firebase_api_key"]?.toString() ?: ""
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "LOGO_API_KEY",
            secretsProperties["logo_api_key"]?.toString() ?: ""
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "APP_TYPE",
            appConfigProperties["config.app_type"].toString()
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "APP_VERSION",
            libs.versions.versionName.get()
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "POST_HOG_API_KEY",
            secretsProperties["post_hog_api_key"]?.toString() ?: ""
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "POST_HOG_HOST",
            secretsProperties["post_hog_host"]?.toString() ?: ""
        )
        buildConfigField(
            FieldSpec.Type.BOOLEAN,
            "SHOW_VAULT_TYPE_SELECTION",
            appConfigProperties["config.show_vault_type_selection"]?.toString() ?: "false"
        )
    }

}