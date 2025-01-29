package org.emes.todo

import javafx.util.StringConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DatePickerStringConverter(
    private val dateFormatter: DateTimeFormatter
) : StringConverter<LocalDate?>() {

    override fun toString(date: LocalDate?): String {
        return if (date != null) {
            dateFormatter.format(date)
        } else {
            ""
        }
    }

    override fun fromString(string: String?): LocalDate? {
        return if (string != null && !string.isEmpty()) {
            LocalDate.parse(string, dateFormatter)
        } else {
            null
        }
    }
}