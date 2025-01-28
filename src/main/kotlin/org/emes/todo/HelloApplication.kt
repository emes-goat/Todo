package org.emes.todo

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback


class HelloApplication : Application() {

    override fun start(stage: Stage) {
        setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)

        val root = VBox()
        val newTodoView = NewTodoView { title, dueDate -> viewModel.save(title, dueDate) }
        root.children.add(newTodoView)

        val listView = ListView<Todo>(viewModel.todos)
        listView.cellFactory = Callback { lv -> TodoView { id -> viewModel.complete(id) } }
        root.children.add(listView)

        val scene = Scene(root, 400.0, 400.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

private val viewModel = ViewModel()

fun main() {
    Application.launch(HelloApplication::class.java)
}