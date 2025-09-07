package com.thejohnsondev.common

enum class AppType {
    DEV,
    DEMO,
    PROD;

    companion object {
        fun from(value: String): AppType {
            return entries.firstOrNull { it.name == value } ?: DEV
        }
    }
}