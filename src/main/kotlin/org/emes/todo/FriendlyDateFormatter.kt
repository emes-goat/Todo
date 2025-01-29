package org.emes.todo

import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class FriendlyDateFormatter(
    private val clock: Clock,
    private val defaultDueFormatter: DateTimeFormatter,
    private val locale: Locale
) {

    private val shortDateFormatter = DateTimeFormatter.ofPattern("d MMM", locale)

    fun format(due: LocalDate): String {
        val today = LocalDate.now(clock)

        return when {
            due.isBefore(today) -> {
                "Overdue"
            }

            due.isEqual(today) -> {
                "${due.format(shortDateFormatter)} - Today"
            }

            due.isEqual(today.plusDays(1)) -> {
                "${due.format(shortDateFormatter)} - Tomorrow"
            }

            due in today.plusDays(2)..today.plusDays(6) -> {
                "${due.format(shortDateFormatter)} - ${getDayOfWeek(due)}"
            }

            due.year == today.year -> {
                due.format(shortDateFormatter)
            }

            else -> {
                due.format(defaultDueFormatter)
            }
        }
    }

    private fun getDayOfWeek(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, locale)
    }
}