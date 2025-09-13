plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    val binaryName = "PassGuardMacOSBiometricCheck"
    macosX64 {
        binaries {
            executable {
                baseName = binaryName
                linkerOpts("-framework", "LocalAuthentication")
                debuggable = false
                optimized = true
                entryPoint("com.thejohnsondev.machelper.main")
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                baseName = binaryName
                linkerOpts("-framework", "LocalAuthentication")
                debuggable = false
                optimized = true
                entryPoint("com.thejohnsondev.machelper.main")
            }
        }
    }

    sourceSets {
        val appleMain by creating {
            dependencies {
                implementation(project(":core:biometric"))

                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val macosArm64Main by getting {
            dependsOn(appleMain)
        }
        val macosX64Main by getting {
            dependsOn(appleMain)
        }
    }
}