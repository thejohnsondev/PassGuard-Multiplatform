package com.thejohnsondev.platform.storage

import java.util.prefs.Preferences

class DesktopSecureStorage: SecureStorage {
    private val prefs = Preferences.userRoot().node("vault_secrets")

    override fun save(key: String, value: String) {
        prefs.put(key, value)
    }

    override fun read(key: String): String? {
        return prefs.get(key, null)
    }

    override fun remove(key: String) {
        prefs.remove(key)
    }

}