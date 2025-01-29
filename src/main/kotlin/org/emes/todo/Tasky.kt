package org.emes.todo

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import org.emes.todo.io.PlaintextIO
import org.emes.todo.view.DatePickerStringConverter
import org.emes.todo.view.FriendlyDateFormatter
import org.emes.todo.view.NewTaskView
import org.emes.todo.view.TaskListCellView
import java.time.Clock


class Tasky : Application() {

    override fun start(stage: Stage) {
        setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)

        val root = VBox().apply {
            padding = insetsOf(DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET)
            spacing = 12.0
        }
        val newTaskView =
            NewTaskView(DatePickerStringConverter(DEFAULT_DATE_FORMATTER)) { content, due ->
                viewModel.save(content, due)
            }
        root.children.add(newTaskView)

        val listView = ListView<Task>(viewModel.tasks)
        listView.cellFactory =
            Callback { _ -> TaskListCellView(friendlyDateFormatter) { id -> viewModel.close(id) } }
        root.children.add(listView)

        val scene = Scene(root, 500.0, 400.0)
        stage.title = "Tasky"
        stage.scene = scene
        stage.show()
    }
}

private val io = PlaintextIO(FILE_PATH)
private val friendlyDateFormatter =
    FriendlyDateFormatter(Clock.systemDefaultZone(), DEFAULT_DATE_FORMATTER, DEFAULT_LOCALE)
private val viewModel = ViewModel(io)

fun main() {
    Application.launch(Tasky::class.java)
}