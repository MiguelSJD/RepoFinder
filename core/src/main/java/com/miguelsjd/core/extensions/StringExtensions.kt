package com.miguelsjd.core.extensions

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

fun String.formatIsoDateToLocalDay(
    outputPattern: String = "dd MMMM yyyy"
): String {
    val locale = Locale.getDefault()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val zonedUtc = ZonedDateTime.parse(this)
        val local = zonedUtc.withZoneSameInstant(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(outputPattern, locale)
        local.format(formatter)
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale)
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val date = parser.parse(this) ?: return this
        val formatter = SimpleDateFormat(outputPattern, locale)
        formatter.timeZone = TimeZone.getDefault()
        formatter.format(date)
    }
}