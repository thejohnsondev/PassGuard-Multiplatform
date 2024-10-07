package org.thejohnsondev.vault

import android.app.Application
import org.thejohnsondev.vault.di.KoinInitializer

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }

}