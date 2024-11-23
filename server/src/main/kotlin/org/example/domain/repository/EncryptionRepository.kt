package org.example.domain.repository

import java.util.Random

interface EncryptionRepository {
    suspend fun encryptEntitySecrete(publicKey: String, entitySecrete: String): String
    fun generate32BitSecretPhrase(): String

    fun hashPassword(password:String):String
    fun verifyHashedPassword(password: String,hashedPassword:String):Boolean
}