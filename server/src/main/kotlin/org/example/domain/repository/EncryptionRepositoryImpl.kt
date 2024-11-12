package org.example.domain.repository

import example.com.utils.CircleConfig
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Security
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class EncryptionRepositoryImpl(): EncryptionRepository {
    override suspend fun encryptEntitySecrete(publicKey: String, entitySecrete:String): String {
        // Add Bouncy Castle as a Security Provider
        Security.addProvider(BouncyCastleProvider())

        // Convert the hexadecimal secret to bytes
        val plaintextBytes = Hex.decode(entitySecrete)

        // Convert PEM to PublicKey object
        val loadedPublicKey = loadPublicKeyFromPem(publicKey)

        // Encrypt the data using RSA/OAEP with SHA-256
        val encryptedData = encryptWithPublicKey(plaintextBytes, loadedPublicKey)

        // Encode the encrypted data to base64
        return Base64.getEncoder().encodeToString(encryptedData)
    }

    override fun generate32BitSecretPhrase(): String {
        return UUID.randomUUID().toString()
    }

    private fun loadPublicKeyFromPem(pem: String): PublicKey {
        val pemFormatted = pem
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val keyBytes = Base64.getDecoder().decode(pemFormatted)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(spec)
    }

    private fun encryptWithPublicKey(data: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(data)
    }

}