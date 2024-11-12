package org.example.domain.repository

import java.util.Random

interface EncryptionRepository {
    suspend fun encryptEntitySecrete(publicKey: String, entitySecrete: String): String
    fun generate32BitSecretPhrase(): String
}