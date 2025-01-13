package com.thejohnsondev.common.utils

import com.thejohnsondev.common.empty
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}

inline fun <reified T> String?.fromJson(): T {
    return Json.decodeFromJsonElement(Json.parseToJsonElement(this ?: String.Companion.empty))
}

fun String.hidden(): String {
    return replace(Regex("[\\s\\S]"), "*")
}

fun getPrettyErrorMessage(error: String?): String {
    return error ?: "Unknown error"
}