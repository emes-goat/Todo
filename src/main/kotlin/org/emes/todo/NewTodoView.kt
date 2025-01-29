package org.emes.todo

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTodoView(
    dateFormatter: DateTimeFormatter,
    private val saveClicked: (String, LocalDate) -> Unit
) : VBox() {

    private val title = TextField().apply {
        promptText = "Task name"
    }
    private val tomorrow = Button("Tomorrow").apply {
        onAction = EventHandler { _ -> dueDate.valueProperty().set(LocalDate.now().plusDays(1)) }
    }
    private val dueDate = DatePicker(LocalDate.now()).apply {
        converter = converter(dateFormatter)
    }
    private val save = Button("Save").apply {
        disableProperty().bind(Bindings.isEmpty(title.textProperty()))
        onAction = EventHandler { _ ->
            saveClicked(title.text, dueDate.value)
            title.text = ""
            dueDate.value = LocalDate.now()
        }
    }
    private val secondRow = HBox().apply {
        padding = insetsOf(top = DEFAULT_INSET)
        children.addAll(tomorrow, dueDate, save)
    }

    init {
        this.children.addAll(title, secondRow)
    }

    private fun converter(dateFormatter: DateTimeFormatter): StringConverter<LocalDate?> {
        return object : StringConverter<LocalDate?>() {
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
    }
}