package org.emes.todo

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NewTodoView(private val saveClicked: (String, LocalDate) -> Unit) : VBox() {
    private val title = TextField()
    private val dueDate = DatePicker(LocalDate.now())
    private val save = Button("Save")

    init {
        dueDate.converter = object : StringConverter<LocalDate?>() {
            var pattern: String = "dd-MM-yyyy"
            var dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

            init {
                dueDate.promptText = pattern.lowercase(Locale.getDefault())
            }

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

        val tomorrow = Button("Tomorrow")
        tomorrow.setOnAction {
            dueDate.valueProperty().set(LocalDate.now().plusDays(1))
        }

        save.disableProperty().bind(Bindings.isEmpty(title.textProperty()))
        save.onAction = EventHandler { _ ->
            saveClicked(title.text, dueDate.value)
            title.text = ""
            dueDate.value = LocalDate.now()
        }
        this.children.addAll(title, dueDate, tomorrow, save)
    }
}