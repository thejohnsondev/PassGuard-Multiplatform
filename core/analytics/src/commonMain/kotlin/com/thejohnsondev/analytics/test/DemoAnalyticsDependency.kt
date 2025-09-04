package com.thejohnsondev.analytics.test

import com.thejohnsondev.analytics.AnalyticsPlatform
import com.thejohnsondev.analytics.di.AnalyticsDependency

class DemoAnalyticsDependency: AnalyticsDependency {
    override fun getAnalyticsPlatform(): AnalyticsPlatform {
        return TestAnalyticsPlatform()
    }
}