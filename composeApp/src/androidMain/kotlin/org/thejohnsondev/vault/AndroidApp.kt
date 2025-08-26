package org.thejohnsondev.vault

import android.app.Application
import com.thejohnsondev.analytics.di.AndroidAnalyticsDependency
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import org.thejohnsondev.vault.di.KoinInitializer

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val platformDependency = AndroidPlatformDependency(this)
        val analyticsDependency = AndroidAnalyticsDependency(this)
        KoinInitializer(
            context = applicationContext,
            platformDependency = platformDependency,
            analyticsDependency = analyticsDependency
        ).init()
    }

}