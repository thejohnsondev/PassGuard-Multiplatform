package com.thejohnsondev.common.encryption


actual object EncryptionUtils {

    actual fun encrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?
    ): String {
        return "" // TODO Implement
    }

    actual fun decrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?
    ): String {
        return "" // TODO Implement
    }

}