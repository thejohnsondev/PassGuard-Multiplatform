package com.thejohnsondev.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

val DEFAULT_SIMPLE_FORMAT = LocalDateTime.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    dayOfMonth()
    char(' ')
    char('a')
    char('t')
    char(' ')
    hour()
    char(':')
    minute()
}

val EXPORT_FILE_TIME_FORMAT = LocalDateTime.Format {
    dayOfMonth()
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    year()
    hour()
    minute()
    second()
}

/**
 * Extension function to parse a nullable String representing a timestamp into a formatted date-time string.
 *
 * @receiver String? The nullable String to be parsed.
 * @param format DateTimeFormat<LocalDateTime> The format to use for parsing. Defaults to DEFAULT_SIMPLE_FORMAT.
 * @return String? The formatted date-time string, or the original string if parsing fails.
 */
fun String?.parseTime(format: DateTimeFormat<LocalDateTime> = DEFAULT_SIMPLE_FORMAT): String? {
    if (this == null) return null
    val timeStampValue = this.toLongOrNull()
    timeStampValue ?: return this
    val instant = Instant.fromEpochSeconds(timeStampValue)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatted = localDateTime.format(format)
    return formatted
}

fun getCurrentTimeStamp(): String {
    return Clock.System.now().epochSeconds.toString()
}

fun getCurrentTimeMillis(): Long {
    return Clock.System.now().toEpochMilliseconds()
}

fun String.getTimeDifferenceInMillis(): Long {
    val currentTime = Clock.System.now().epochSeconds
    val timeStampValue = this.toLongOrNull()
    return currentTime - (timeStampValue ?: 0)
}

fun Long?.toAgeInDays(): Long {
    val nowMillis = getCurrentTimeMillis()
    return this?.let {
        (nowMillis - it * 1000) / (1000L * 60L * 60L * 24L)
    } ?: 0L
}