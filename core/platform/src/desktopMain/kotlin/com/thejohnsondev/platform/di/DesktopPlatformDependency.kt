package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.DesktopKeyGenerator
import com.thejohnsondev.platform.KeyGenerator

class DesktopPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = DesktopKeyGenerator()
}