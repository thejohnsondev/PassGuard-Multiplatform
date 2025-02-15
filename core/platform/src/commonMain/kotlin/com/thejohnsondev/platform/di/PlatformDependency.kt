package com.thejohnsondev.platform.di

import com.thejohnsondev.platform.KeyGenerator

interface PlatformDependency {
    fun getKeyGenerator(): KeyGenerator
}