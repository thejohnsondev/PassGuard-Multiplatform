package com.thejohnsondev.platform.encryption

import com.thejohnsondev.platform.SECRET_KEY_SIZE
import java.security.SecureRandom

class DesktopKeyGenerator: KeyGenerator {
    override fun generateKeyWithPBKDF(password: String): ByteArray {
        return PBKDFUtils.pbkdf2(
            "HmacSHA256",
            password.toByteArray(),
            password.toByteArray(),
            1000,
            16
        )
    }

    override fun generateSecretKey(): ByteArray {
        val keyGen = javax.crypto.KeyGenerator.getInstance("AES")
        keyGen.init(SECRET_KEY_SIZE, SecureRandom())
        return keyGen.generateKey().encoded
    }
}