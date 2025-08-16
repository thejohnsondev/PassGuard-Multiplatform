package com.thejohnsondev.localization

import platform.Foundation.NSUserDefaults

actual class AppLocaleManager {
    actual fun changeLocale(languageIsoCode: String) {
        NSUserDefaults.standardUserDefaults
            .setObject(arrayListOf(languageIsoCode), "AppleLanguages")
    }
}