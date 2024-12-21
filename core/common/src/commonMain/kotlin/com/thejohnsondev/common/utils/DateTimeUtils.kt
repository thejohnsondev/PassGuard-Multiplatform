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
    char(',')
    year()
}

fun String?.parseTime(format: DateTimeFormat<LocalDateTime> = DEFAULT_SIMPLE_FORMAT): String? {
    if (this == null) return null
    val timeStampValue = this.toLongOrNull()
    timeStampValue ?: throw IllegalArgumentException("Invalid time stamp value: $this")
    val instant = Instant.fromEpochSeconds(timeStampValue)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatted = localDateTime.format(format)
    return formatted
}

fun getCurrentTimeStamp(): String {
    return Clock.System.now().epochSeconds.toString()
}