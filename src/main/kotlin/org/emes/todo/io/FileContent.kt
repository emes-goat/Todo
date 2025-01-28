package org.emes.todo.io

import kotlinx.serialization.Serializable

@Serializable
internal data class FileContent(
    val passwordSalt: String,
    val encryptionSalt: String,
    val ciphertext: String,
    val tag: String
)