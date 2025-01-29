package org.emes.todo

import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class FriendlyDateFormatter(private val clock: Clock) {

    private val longDateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    private val shortDateFormatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)

    fun format(date: LocalDate): String {
        val today = LocalDate.now(clock)

        return if (date.isBefore(today)) {
            "Overdue"
        } else if (date.isEqual(today)) {
            "${date.format(shortDateFormatter)} - Today"
        } else if (date.isEqual(today.plusDays(1))) {
            "${date.format(shortDateFormatter)} - Tomorrow"
        } else if (date in today.plusDays(2)..today.plusDays(6)) {
            "${date.format(shortDateFormatter)} - ${getDayOfWeek(date)}"
        } else if (date.year == today.year) {
            date.format(shortDateFormatter)
        } else {
            date.format(longDateFormatter)
        }
    }

    private fun getDayOfWeek(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    }
}