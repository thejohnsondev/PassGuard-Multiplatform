package com.thejohnsondev.platform.encryption

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AndroidEncryptionUtils: EncryptionUtils {
    override fun encrypt(input: String, key: ByteArray, iv: ByteArray?): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv ?: key.sliceArray(0 until cipher.blockSize))
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        val encrypted = cipher.doFinal(input.toByteArray())
        return java.util.Base64.getEncoder().encodeToString(encrypted)
    }

    override fun decrypt(input: String, key: ByteArray, iv: ByteArray?): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv ?: key.sliceArray(0 until cipher.blockSize))
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        val plainText = cipher.doFinal(java.util.Base64.getDecoder().decode(input))
        return String(plainText)
    }
}