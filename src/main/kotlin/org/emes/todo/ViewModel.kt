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
    private val _todos: ObservableList<Todo> = FXCollections.observableArrayList<Todo>()
    val todos = SortedList(_todos, Comparator<Todo> { a, b -> a.dueDate.compareTo(b.dueDate) })
    private val threadPool = Executors.newFixedThreadPool(1)
    private val io = PlaintextIO()
    private val filePath = Paths.get("todos.json")

    init {
        CompletableFuture.supplyAsync({ io.read(filePath) }, threadPool)
            .whenComplete { todos, exception -> _todos.addAll(todos) }
    }

    fun save(title: String, dueDate: LocalDate) {
        _todos.add(Todo(UUID.randomUUID(), title, dueDate))
        io.write(filePath, _todos.toList())
    }

    fun complete(id: UUID) {
        _todos.removeIf { it.id == id }
        io.write(filePath, _todos.toList())
    }
}