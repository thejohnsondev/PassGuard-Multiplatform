package com.thejohnsondev.common.utils

import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

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

@OptIn(ExperimentalTime::class)
fun String?.parseTime(format: DateTimeFormat<LocalDateTime> = DEFAULT_SIMPLE_FORMAT): String? {
    if (this == null) return null
    val timeStampValue = this.toLongOrNull()
    timeStampValue ?: return this
    val instant = Instant.fromEpochSeconds(timeStampValue)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatted = localDateTime.format(format)
    return formatted
}

@OptIn(ExperimentalTime::class)
fun getCurrentTimeStamp(): String {
    return Clock.System.now().epochSeconds.toString()
}

@OptIn(ExperimentalTime::class)
fun getCurrentTimeMillis(): Long {
    return Clock.System.now().toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
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