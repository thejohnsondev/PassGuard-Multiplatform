package com.thejohnsondev.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import org.thejohnsondev.vault.database.VaultDatabase
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun create(): SqlDriver {
        return NativeSqliteDriver(VaultDatabase.Schema, "VaultDatabase.db")
    }

}