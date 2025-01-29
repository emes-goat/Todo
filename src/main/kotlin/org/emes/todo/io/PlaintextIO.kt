package org.emes.todo.io

import kotlinx.serialization.json.Json
import org.emes.todo.Task
import java.nio.file.Files
import java.nio.file.Path

class PlaintextIO {

    companion object {
        private val FILE_CHARSET = Charsets.UTF_8
    }

    fun write(filePath: Path, tasks: List<Task>) {
        val json = Json.encodeToString(tasks)
        Files.writeString(filePath, json, FILE_CHARSET)
    }

    fun read(filePath: Path): List<Task> {
        if (Files.notExists(filePath)) {
            return emptyList()
        }

        val json = Files.readString(filePath, FILE_CHARSET)
        return Json.decodeFromString<List<Task>>(json)
    }
}