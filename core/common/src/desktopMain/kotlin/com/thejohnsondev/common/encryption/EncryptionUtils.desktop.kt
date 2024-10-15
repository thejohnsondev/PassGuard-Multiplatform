package com.thejohnsondev.common.encryption

actual object EncryptionUtils {
    actual fun generateKey(password: String): ByteArray {
        return PBKDFUtils.pbkdf2("HmacSHA256", password.toByteArray(), password.toByteArray(), 1000, 16)
    }

    actual fun encrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?
    ): String {
        TODO("Not yet implemented")
    }

    actual fun decrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?
    ): String {
        TODO("Not yet implemented")
    }
}