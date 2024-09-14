package com.bshpanchuk.presentation.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAccessor

object DateUtils {

    fun TemporalAccessor.formatToTime(
        timeStyle: FormatStyle = FormatStyle.SHORT
    ): String {
        return DateTimeFormatter
            .ofLocalizedTime(timeStyle)
            .withZone(ZoneId.systemDefault())
            .format(this)
    }

    fun TemporalAccessor.formatToDate(
        dateStyle: FormatStyle = FormatStyle.SHORT
    ): String {
        return DateTimeFormatter
            .ofLocalizedDate(dateStyle)
            .withZone(ZoneId.systemDefault())
            .format(this)
    }

    fun TemporalAccessor.formatToDateTime(
        dateStyle: FormatStyle = FormatStyle.SHORT,
        timeStyle: FormatStyle = FormatStyle.SHORT
    ): String {
        return DateTimeFormatter
            .ofLocalizedDateTime(dateStyle, timeStyle)
            .withZone(ZoneId.systemDefault())
            .format(this)
    }
}