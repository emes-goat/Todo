package org.emes.todo

import javafx.geometry.Insets

fun insetsOf(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0): Insets {
    return Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble())
}

const val DEFAULT_INSET = 12