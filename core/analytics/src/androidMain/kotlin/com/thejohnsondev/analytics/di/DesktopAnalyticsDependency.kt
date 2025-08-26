package com.thejohnsondev.analytics.di

import android.content.Context
import com.thejohnsondev.analytics.AnalyticsPlatform
import com.thejohnsondev.analytics.posthog.AndroidPosthogAnalyticsPlatform

class AndroidAnalyticsDependency(
    private val context: Context
) : AnalyticsDependency {
    override fun getAnalyticsPlatform(): AnalyticsPlatform = AndroidPosthogAnalyticsPlatform(context)
}