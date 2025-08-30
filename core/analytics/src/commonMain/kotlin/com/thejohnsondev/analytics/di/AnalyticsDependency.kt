package com.thejohnsondev.analytics.di

import com.thejohnsondev.analytics.AnalyticsPlatform

interface AnalyticsDependency {
    fun getAnalyticsPlatform(): AnalyticsPlatform
}