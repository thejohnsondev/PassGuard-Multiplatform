package com.thejohnsondev.common.encryption

actual object PBKDFUtils {
    actual fun pbkdf2(
        alg: String?,
        P: ByteArray?,
        S: ByteArray,
        c: Int,
        dkLen: Int
    ): ByteArray {
        return ByteArray(0) // TODO Implement
    }
}