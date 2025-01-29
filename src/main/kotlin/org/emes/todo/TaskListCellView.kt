package org.emes.todo

import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.UUID

class TaskListCellView(
    private val friendlyDateFormatter: FriendlyDateFormatter,
    private val closeClicked: (UUID) -> Unit
) : ListCell<Task?>() {
    private val layout = HBox().apply {
        alignment = Pos.CENTER_LEFT
    }
    private val close = CheckBox().apply {
        setOnAction { _ -> closeClicked(this@TaskListCellView.id!!) }
    }
    private val vbox = VBox().apply {
        padding = insetsOf(DEFAULT_INSET / 2, DEFAULT_INSET / 2, 0, DEFAULT_INSET / 2)
    }
    private val due = Label().apply {
        styleClass.addAll("text-small", "text-muted")
    }
    private val title = Label().apply {
    }
    private var id: UUID? = null

    init {
        layout.children.addAll(close, vbox)
        vbox.children.addAll(due, title)
        alignment = Pos.CENTER
    }

    public override fun updateItem(item: Task?, isEmpty: Boolean) {
        super.updateItem(item, isEmpty)

        if (!isEmpty && (item != null)) {
            if (item.isOverdue()) {
                due.styleClass.add("warning")
                title.styleClass.add("warning")
            }
            due.text = friendlyDateFormatter.format(item.due)
            title.text = item.content
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