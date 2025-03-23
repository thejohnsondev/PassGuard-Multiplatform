package com.thejohnsondev.common

enum class AppType {
    REAL,
    DEMO;

    companion object {
        fun from(value: String): AppType {
            return entries.firstOrNull { it.name == value } ?: REAL
        }
    }
}