package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.AndroidKeyGenerator
import com.thejohnsondev.platform.KeyGenerator

class AndroidPlatformDependency: PlatformDependency {
    override fun getKeyGenerator(): KeyGenerator = AndroidKeyGenerator()
}