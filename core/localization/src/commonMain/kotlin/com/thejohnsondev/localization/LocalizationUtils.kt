package com.thejohnsondev.localization

import com.thejohnsondev.datastore.PreferencesDataStore

class LocalizationUtils(
    private val dataStore: PreferencesDataStore,
    private val appLocaleManager: AppLocaleManager
) {
    suspend fun getSelectedLanguage(): Language {
        val selectedLanguageCode = dataStore.getSelectedLanguage()
        val language = Language.fromIso2Code(selectedLanguageCode)
        return language
    }

    suspend fun setSelectedLanguage(language: Language) {
        dataStore.updateSelectedLanguage(language.iso2Code)
        appLocaleManager.changeLocale(language.iso2Code)
    }
}

expect class AppLocaleManager {
    fun changeLocale(languageIsoCode: String)
}
