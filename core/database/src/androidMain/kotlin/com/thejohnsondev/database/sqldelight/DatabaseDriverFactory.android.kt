package com.thejohnsondev.database.sqldelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.thejohnsondev.vault.database.VaultDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver =
        AndroidSqliteDriver(VaultDatabase.Schema, context, "VaultDatabase.db")
}