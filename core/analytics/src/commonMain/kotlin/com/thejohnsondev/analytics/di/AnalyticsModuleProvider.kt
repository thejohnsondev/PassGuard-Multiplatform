package com.thejohnsondev.analytics.di

import com.thejohnsondev.analytics.AnalyticsPlatform
import org.koin.dsl.module

data class AnalyticsModuleProvider(
    val analyticsDependency: AnalyticsDependency
) {
    fun generateAnalyticsModule() = module {
        single<AnalyticsPlatform> { analyticsDependency.getAnalyticsPlatform() }
    }
}