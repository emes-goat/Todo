package org.emes.todo

import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.HBox
import java.util.UUID

class TodoView(private val completeClicked: (UUID) -> Unit) : ListCell<Todo?>() {
    private val layout = HBox()
    private val complete = CheckBox()
    private val title = Label()
    private val dueDate = Label()
    private var id: UUID? = null

    init {
        complete.setOnAction { _ -> completeClicked(id!!) }
        layout.children.addAll(complete, dueDate, title)
    }

    public override fun updateItem(item: Todo?, isEmpty: Boolean) {
        super.updateItem(item, isEmpty)

        if (!isEmpty && (item != null)) {
            if (item.isOverdue()) {
                dueDate.styleClass.add("warning")
                title.styleClass.add("warning")
            }
            dueDate.text = FriendlyDate.convert(item.dueDate)
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