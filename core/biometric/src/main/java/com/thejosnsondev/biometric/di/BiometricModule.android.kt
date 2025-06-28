package com.thejosnsondev.biometric.di

import com.thejosnsondev.biometric.BiometricAuthenticator
import org.koin.dsl.module

actual val biometricModule = module {
    single { BiometricAuthenticator(get()) }
}