package com.thejohnsondev.database.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.thejohnsondev.vault.database.VaultDatabase
import java.io.File

actual class DatabaseDriverFactory {

    private val appDir: File
        get() {
            val os = System.getProperty("os.name").lowercase()
            return when {
                os.contains("win") -> {
                    File(
                        System.getenv("AppData"),
                        "VaultDatabase/db"
                    ) // "C:\Users<username>\AppData\Roaming\VaultDatabase\db"
                }

                os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                    File(
                        System.getProperty("user.home"), ".VaultDatabase"
                    ) // "/home/<username>/.VaultDatabase"
                }

                os.contains("mac") -> {
                    File(
                        System.getProperty("user.home"),
                        "Library/Application Support/VaultDatabase"
                    ) // "/Users/<username>/Library/Application Support/VaultDatabase"
                }

                else -> error("Unsupported operating system")
            }
        }

    private val databaseFile: File
        get() = File(appDir.also { if (!it.exists()) it.mkdirs() }, "VaultDatabase.db")

    actual fun create(): SqlDriver {
        return JdbcSqliteDriver(
            url = "jdbc:sqlite:${databaseFile.absolutePath}",
        ).also { db ->
            VaultDatabase.Schema.create(db)
        }
    }
}