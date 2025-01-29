package org.emes.todo

import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.UUID

class TodoView(
    private val friendlyDateFormatter: FriendlyDateFormatter,
    private val completeClicked: (UUID) -> Unit
) : ListCell<Todo?>() {
    private val layout = HBox().apply {
        alignment = Pos.CENTER_LEFT
    }
    private val complete = CheckBox().apply {
        setOnAction { _ -> completeClicked(this@TodoView.id!!) }
    }
    private val vbox = VBox().apply {
        padding = insetsOf(DEFAULT_INSET / 2, DEFAULT_INSET / 2, 0, DEFAULT_INSET / 2)
    }
    private val dueDate = Label().apply {
        styleClass.addAll("text-small", "text-muted")
    }
    private val title = Label().apply {
    }
    private var id: UUID? = null

    init {
        layout.children.addAll(complete, vbox)
        vbox.children.addAll(dueDate, title)
        alignment = Pos.CENTER
    }

    public override fun updateItem(item: Todo?, isEmpty: Boolean) {
        super.updateItem(item, isEmpty)

        if (!isEmpty && (item != null)) {
            if (item.isOverdue()) {
                dueDate.styleClass.add("warning")
                title.styleClass.add("warning")
            }
            dueDate.text = friendlyDateFormatter.format(item.dueDate)
            title.text = item.title
            id = item.id

            graphic = layout
            text = null
        } else {
            id = null

            graphic = null
            text = null
        }
    }
}