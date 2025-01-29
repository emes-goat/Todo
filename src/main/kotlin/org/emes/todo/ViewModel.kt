package org.emes.todo

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import org.emes.todo.io.PlaintextIO
import java.nio.file.Paths
import java.time.LocalDate
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ViewModel {
    private val _tasks: ObservableList<Task> = FXCollections.observableArrayList<Task>()
    val tasks = SortedList(_tasks, Comparator<Task> { a, b -> a.due.compareTo(b.due) })
    private val threadPool = Executors.newFixedThreadPool(1)
    private val io = PlaintextIO()
    private val filePath = Paths.get("todos.json")

    init {
        CompletableFuture.supplyAsync({ io.read(filePath) }, threadPool)
            .whenComplete { todos, exception ->
                _tasks.addAll(todos)
                threadPool.shutdownNow()
            }
    }

    fun save(title: String, dueDate: LocalDate) {
        _tasks.add(Task(UUID.randomUUID(), title, dueDate))
        io.write(filePath, _tasks.toList())
    }

    fun complete(id: UUID) {
        _tasks.removeIf { it.id == id }
        io.write(filePath, _tasks.toList())
    }
}