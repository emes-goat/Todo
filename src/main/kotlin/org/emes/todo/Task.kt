package org.emes.todo

import kotlinx.serialization.Serializable
import org.emes.todo.io.LocalDateSerializer
import org.emes.todo.io.UUIDSerializer
import java.time.LocalDate
import java.util.UUID

@Serializable
data class Task(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val content: String,
    @Serializable(with = LocalDateSerializer::class)
    val due: LocalDate,
    val mode: RecurringMode = RecurringMode.NO_RECURRENCE,
    val argument: String = ""
) {

    fun isOverdue(): Boolean {
        return due.isBefore(LocalDate.now())
    }
}