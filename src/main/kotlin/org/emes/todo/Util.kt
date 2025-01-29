package org.emes.todo

import javafx.geometry.Insets
import java.nio.file.Path
import java.nio.file.Paths
import java.time.format.DateTimeFormatter
import java.util.Locale

val FILE_PATH: Path = Paths.get("todos.json")
val DEFAULT_LOCALE: Locale = Locale.ENGLISH
val DEFAULT_DATE_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMM yyyy", DEFAULT_LOCALE)

fun insetsOf(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0): Insets {
    return Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble())
}

const val DEFAULT_INSET = 12