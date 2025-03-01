package org.thejohnsondev.vault

import android.app.Application
import com.thejohnsondev.platform.di.AndroidPlatformDependency
import org.thejohnsondev.vault.di.KoinInitializer

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val platformDependency = AndroidPlatformDependency(this)
        KoinInitializer(
            context = applicationContext,
            platformDependency = platformDependency
        ).init()
    }

}