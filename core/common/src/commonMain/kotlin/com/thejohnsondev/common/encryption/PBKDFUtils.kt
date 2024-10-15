package com.thejohnsondev.common.encryption

expect object PBKDFUtils {
    fun pbkdf2(alg: String?, P: ByteArray?, S: ByteArray, c: Int, dkLen: Int): ByteArray
}