package org.emes.todo

import kotlinx.serialization.Serializable
import org.emes.todo.io.LocalDateSerializer
import org.emes.todo.io.UUIDSerializer
import java.time.LocalDate
import java.util.UUID

@Serializable
data class Todo(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val title: String,
    @Serializable(with = LocalDateSerializer::class)
    val dueDate: LocalDate
) {

    fun isOverdue(): Boolean {
        return dueDate.isBefore(LocalDate.now())
    }
}