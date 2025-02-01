package com.thejohnsondev.common.encryption

import com.thejohnsondev.common.SECRET_KEY_SIZE
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual object EncryptionUtils {

    actual fun generateSecretKey(): ByteArray {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(SECRET_KEY_SIZE, SecureRandom())
        return keyGen.generateKey().encoded
    }

    actual fun generateKeyWithPBKDF(password: String): ByteArray {
        return PBKDFUtils.pbkdf2(
            "HmacSHA256",
            password.toByteArray(),
            password.toByteArray(),
            1000,
            16
        )
    }

    actual fun encrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?,
    ): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv ?: key.sliceArray(0 until cipher.blockSize))
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        val encrypted = cipher.doFinal(input.toByteArray())
        return java.util.Base64.getEncoder().encodeToString(encrypted)
    }

    actual fun decrypt(
        input: String,
        key: ByteArray,
        iv: ByteArray?,
    ): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv ?: key.sliceArray(0 until cipher.blockSize))
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        val plainText = cipher.doFinal(java.util.Base64.getDecoder().decode(input))
        return String(plainText)
    }
}