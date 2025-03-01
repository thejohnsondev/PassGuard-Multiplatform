package com.thejohnsondev.common

expect fun getPlatform(): Platform

enum class Platform {
    JVM,
    ANDROID,
    IOS_DEVICE,
    IOS_SIMULATOR
}