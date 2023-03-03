package com.example.templateemptyproject.utils

import java.text.SimpleDateFormat
import java.util.*

// Ex: "2023-03-03T18:00:00Z"
const val LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val SHORT_DATE_FORMAT = "dd.MM.yyyy"
const val DAY_FORMAT = "MM"

// Top-level
fun getShortDateFormatFromStringLongDate(
    newDatePattern: String,
    dateValue: String?
): String? {
    val originalFormatter = SimpleDateFormat(LONG_DATE_FORMAT, Locale.getDefault())
    val targetFormatter = SimpleDateFormat(newDatePattern, Locale.getDefault())
    val date: Date? = getDateInstanceFromStringLongFormatValue(dateValue)
    return date?.run { targetFormatter.format(date) }
}

fun getDayFromStringLongDate(dateValue: String?): String{
    val date: Date? = getDateInstanceFromStringLongFormatValue(dateValue)

    val calendar = Calendar.getInstance()
    date?.let { calendar.time = it }

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tue"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        Calendar.SUNDAY -> "Sun"
        else -> ""
    }
}

 fun getDateInstanceFromStringLongFormatValue(dateValue: String?): Date? {
    val formatterLongDate = SimpleDateFormat(LONG_DATE_FORMAT, Locale.getDefault())
    return try {
        dateValue?.let { formatterLongDate.parse(it) }
    } catch (e: Exception) {
        null
    }
}


