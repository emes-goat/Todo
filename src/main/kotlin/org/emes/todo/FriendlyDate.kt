package org.emes.todo

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object FriendlyDate {

    private val defaultPattern = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun convert(date: LocalDate): String {
        val today = LocalDate.now()

        return if (date.isBefore(today)) {
            "Overdue"
        } else if (date.isEqual(today)) {
            "Today"
        } else if (date.isEqual(today.plusDays(1))) {
            "Tomorrow"
        } else if (date.isEqual(today.plusDays(2))) {
            today.plusDays(2).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        } else {
            date.format(defaultPattern)
        }
    }
}