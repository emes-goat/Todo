package org.emes.todo.io

import kotlinx.serialization.json.Json
import org.emes.todo.IO
import org.emes.todo.Task
import java.nio.file.Files
import java.nio.file.Path

class PlaintextIO(private val filePath: Path) : IO {

    companion object {
        private val FILE_CHARSET = Charsets.UTF_8
    }

    override fun write(tasks: List<Task>) {
        val json = Json.encodeToString(tasks)
        Files.writeString(filePath, json, FILE_CHARSET)
    }

    override fun read(): List<Task> {
        if (Files.notExists(filePath)) {
            return emptyList()
        }

        val json = Files.readString(filePath, FILE_CHARSET)
        return Json.decodeFromString<List<Task>>(json)
    }
}