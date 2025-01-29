package org.emes.todo

interface IO {
    fun write(tasks: List<Task>)
    fun read(): List<Task>
}