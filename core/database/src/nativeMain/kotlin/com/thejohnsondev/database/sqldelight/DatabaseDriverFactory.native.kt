package com.thejohnsondev.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import org.thejohnsondev.vault.database.VaultDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver =
        NativeSqliteDriver(VaultDatabase.Schema, "VaultDatabase.db")
}