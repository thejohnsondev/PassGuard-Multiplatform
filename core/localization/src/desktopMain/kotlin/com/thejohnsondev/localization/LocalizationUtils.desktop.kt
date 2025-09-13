package com.thejohnsondev.localization

import java.util.Locale

actual class AppLocaleManager {
    actual fun changeLocale(languageIsoCode: String) {
        val locale = Locale.forLanguageTag(languageIsoCode)
        Locale.setDefault(locale)
    }
}