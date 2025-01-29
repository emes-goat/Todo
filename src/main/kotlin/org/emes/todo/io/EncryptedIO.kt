package org.emes.todo.io

import kotlinx.serialization.json.Json
import org.emes.todo.Task
import java.nio.file.Files
import java.nio.file.Path
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class EncryptedIO {

    companion object {
        private const val KEY_DERIVATION_FUNCTION = "PBKDF2WithHmacSHA256"
        private const val KEY_DERIVATION_ITERATIONS = 600_000
        private const val AES_KEY_SIZE = 128
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val AES = "AES"
        private val FILE_CHARSET = Charsets.UTF_8
    }

    fun write(filePath: Path, password: CharArray, tasks: List<Task>) {
        val json = Json.encodeToString(tasks)

        val secureRandom = SecureRandom()
        val salt = ByteArray(AES_KEY_SIZE / 8).apply { secureRandom.nextBytes(this) }
        val aad = ByteArray(12).apply { secureRandom.nextBytes(this) }
        val passwordSalt = ByteArray(AES_KEY_SIZE / 8).apply { secureRandom.nextBytes(this) }
        val key = getEncryptedPassword(password, passwordSalt)

        val cipher = Cipher.getInstance(AES_MODE)
        val secretKey = SecretKeySpec(key, AES)
        val gcmParameterSpec = GCMParameterSpec(AES_KEY_SIZE, salt)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec)
        cipher.updateAAD(aad)

        val ciphertext = cipher.doFinal(json.toByteArray())
        val fileContent = FileContent(
            passwordSalt = Base64.Default.encode(passwordSalt),
            encryptionSalt = Base64.Default.encode(salt),
            ciphertext = Base64.Default.encode(ciphertext),
            tag = Base64.Default.encode(aad)
        )

        val fileContentJson = Json.Default.encodeToString(fileContent)
        Files.writeString(filePath, fileContentJson, FILE_CHARSET)
    }

    fun read(filePath: Path, password: CharArray): List<Task> {
        if (Files.notExists(filePath)) {
            return emptyList()
        }

        val fileContentJson = Files.readString(filePath, FILE_CHARSET)
        val fileContent = Json.decodeFromString<FileContent>(fileContentJson)

        val key = getEncryptedPassword(password, Base64.Default.decode(fileContent.passwordSalt))

        val cipher = Cipher.getInstance(AES_MODE)
        val secretKey = SecretKeySpec(key, AES)
        val gcmParameterSpec =
            GCMParameterSpec(AES_KEY_SIZE, Base64.Default.decode(fileContent.encryptionSalt))

        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        cipher.updateAAD(Base64.Default.decode(fileContent.tag))

        val plaintext = cipher.doFinal(Base64.Default.decode(fileContent.ciphertext))
        return Json.decodeFromString<List<Task>>(plaintext.toString(FILE_CHARSET))
    }

    private fun getEncryptedPassword(
        password: CharArray,
        salt: ByteArray
    ): ByteArray {
        val spec = PBEKeySpec(password, salt, KEY_DERIVATION_ITERATIONS, AES_KEY_SIZE)
        return SecretKeyFactory.getInstance(KEY_DERIVATION_FUNCTION).generateSecret(spec).encoded
    }
}