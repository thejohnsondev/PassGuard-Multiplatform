rootProject.name = "VaultMultiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core")
include(":core:common")
include(":core:database")
include(":core:datastore")
include(":core:model")
include(":core:network")
include(":core:platform")
include(":core:sync")
include(":core:ui")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:presentation")
include(":feature:vault:data")
include(":feature:vault:domain")
include(":feature:vault:presentation")
include(":feature:tools:data")
include(":feature:tools:domain")
include(":feature:tools:presentation")
include(":feature:settings:data")
include(":feature:settings:domain")
include(":feature:settings:presentation")
include(":feature:autofill")