package com.thejohnsondev.analytics.di

import com.thejohnsondev.analytics.AnalyticsPlatform
import com.thejohnsondev.analytics.posthog.DesktopPosthogAnalyticsPlatform

class DesktopAnalyticsDependency : AnalyticsDependency {
    override fun getAnalyticsPlatform(): AnalyticsPlatform = DesktopPosthogAnalyticsPlatform()
}