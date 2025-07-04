plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    macosX64 {
        binaries {
            executable {
                baseName = "macOSBiometricHelper"
                linkerOpts("-framework", "LocalAuthentication")
                debuggable = false
                optimized = true
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                baseName = "macOSBiometricHelper"
                linkerOpts("-framework", "LocalAuthentication")
                debuggable = false
                optimized = true
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val macosMain by creating {
            dependsOn(commonMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }
}