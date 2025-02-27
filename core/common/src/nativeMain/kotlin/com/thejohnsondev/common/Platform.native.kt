package com.thejohnsondev.common

import platform.Foundation.NSProcessInfo

actual fun getPlatform(): Platform = if (NSProcessInfo.processInfo.environment["SIMULATOR_DEVICE_NAME"] != null) {
    Platform.IOS_SIMULATOR
} else {
    Platform.IOS_DEVICE
}