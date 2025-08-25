package com.thejohnsondev.analytics.di

import com.thejohnsondev.analytics.posthog.PosthogAnalyticsPlatform
import org.koin.dsl.module

actual val analyticsModule = module {
    single { PosthogAnalyticsPlatform(get()) }
}