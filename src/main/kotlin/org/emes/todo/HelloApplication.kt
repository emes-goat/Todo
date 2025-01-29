package org.emes.todo

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import java.time.Clock
import java.time.format.DateTimeFormatter
import java.util.Locale


class HelloApplication : Application() {

    override fun start(stage: Stage) {
        setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)

        val root = VBox().apply {
            padding = insetsOf(DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET)
            spacing = 12.0
        }
        val newTaskView =
            NewTaskView(DatePickerStringConverter(longDateFormatter)) { title, dueDate ->
                viewModel.save(title, dueDate)
            }
        root.children.add(newTaskView)

        val listView = ListView<Task>(viewModel.tasks)
        listView.cellFactory =
            Callback { _ -> TaskListCellView(friendlyDateFormatter) { id -> viewModel.complete(id) } }
        root.children.add(listView)

        val scene = Scene(root, 500.0, 400.0)
        stage.title = "Todo"
        stage.scene = scene
        stage.show()
    }
}

private val defaultLocale = Locale.ENGLISH
private val longDateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", defaultLocale)

private val friendlyDateFormatter =
    FriendlyDateFormatter(Clock.systemDefaultZone(), longDateFormatter, defaultLocale)
private val viewModel = ViewModel()

fun main() {
    Application.launch(HelloApplication::class.java)
}