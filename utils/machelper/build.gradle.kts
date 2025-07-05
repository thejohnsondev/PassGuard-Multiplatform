plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    macosArm64 {
        binaries {
            executable {
                baseName = "PassGuard MacOS Biometric Check"
                linkerOpts("-framework", "LocalAuthentication")
                debuggable = false
                optimized = true
                entryPoint("com.thejohnsondev.machelper.main")
            }
        }
    }

    sourceSets {
        val macosArm64Main by getting {
            dependencies {
                implementation(project(":core:biometric"))

                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }
}