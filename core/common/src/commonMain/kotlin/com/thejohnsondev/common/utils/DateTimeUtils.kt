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

private val DEFAULT_SIMPLE_FORMAT = LocalDateTime.Format {
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