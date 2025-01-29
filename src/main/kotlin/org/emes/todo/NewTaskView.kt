package org.emes.todo

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.time.LocalDate

class NewTaskView(
    private val datePickerStringConverter: DatePickerStringConverter,
    private val saveClicked: (String, LocalDate) -> Unit
) : VBox() {

    private val content = TextField().apply {
        promptText = "Task name"
    }
    private val tomorrow = Button("Tomorrow").apply {
        onAction = EventHandler { _ -> due.valueProperty().set(LocalDate.now().plusDays(1)) }
    }
    private val due = DatePicker(LocalDate.now()).apply {
        converter = datePickerStringConverter
    }
    private val save = Button("Save").apply {
        disableProperty().bind(Bindings.isEmpty(content.textProperty()))
        onAction = EventHandler { _ ->
            saveClicked(content.text, due.value)
            content.text = ""
            due.value = LocalDate.now()
        }
    }
    private val secondRow = HBox().apply {
        padding = insetsOf(top = DEFAULT_INSET)
        children.addAll(tomorrow, due, save)
    }

    init {
        this.children.addAll(content, secondRow)
    }
}