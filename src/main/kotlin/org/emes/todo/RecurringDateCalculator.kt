package org.emes.todo

import java.time.LocalDate

class RecurringDateCalculator {

    fun calculateNewDue(
        due: LocalDate,
        recurringMode: RecurringMode,
        argument: String
    ): LocalDate {
        require(recurringMode != RecurringMode.NO_RECURRENCE)

        return if (recurringMode == RecurringMode.EVERY_DAY_OF_WEEK) {
            calculateEveryDayOfWeek(due, argument)
        } else {
            LocalDate.now()
        }
    }

    private fun calculateEveryDayOfWeek(
        date: LocalDate,
        string: String
    ): LocalDate {
        return LocalDate.now()
    }
}