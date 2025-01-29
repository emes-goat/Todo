package org.emes.todo

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import java.time.LocalDate
import java.util.UUID
import java.util.concurrent.CompletableFuture

class ViewModel(private val io: IO, loadTasks: Boolean = true) {
    private val _tasks: ObservableList<Task> = FXCollections.observableArrayList<Task>()
    val tasks = SortedList(_tasks, Comparator<Task> { a, b -> a.due.compareTo(b.due) })

    init {
        if (loadTasks) {
            CompletableFuture.supplyAsync { io.read() }
                .whenComplete { todos, exception -> _tasks.addAll(todos) }
        }
    }

    fun save(content: String, due: LocalDate) {
        _tasks.add(Task(UUID.randomUUID(), content, due))
        io.write(_tasks.toList())
    }

    fun close(id: UUID) {
        _tasks.removeIf { it.id == id }
        io.write(_tasks.toList())
    }
}