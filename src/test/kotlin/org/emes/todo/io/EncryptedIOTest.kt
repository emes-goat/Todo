package org.emes.todo.io

import org.emes.todo.Todo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.UUID

@TestInstance(PER_CLASS)
class EncryptedIOTest {

    companion object {
        private val FILE_PATH = Paths.get("file")
    }

    @Test
    fun writeAndRead() {
        val io = EncryptedIO()
        val todos = listOf(Todo(UUID.randomUUID(), "Go shopping", LocalDate.now()))

        io.write(FILE_PATH, "pass".toCharArray(), todos)
        val result = io.read(FILE_PATH, "pass".toCharArray())

        assertEquals(1, result.size)
    }

    @AfterAll
    fun cleanup() {
        Files.deleteIfExists(FILE_PATH)
    }
}