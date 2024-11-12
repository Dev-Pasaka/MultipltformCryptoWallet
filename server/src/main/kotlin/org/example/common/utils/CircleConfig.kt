package example.com.utils

import com.typesafe.config.ConfigFactory

object CircleConfig {
    private val load = ConfigFactory.load()
    val baseUrl = load.getString("ktor.circle.baseUrl") ?: ""
    val apiKey = System.getenv("API_KEY") ?: ""
    val entitySecret = System.getenv("ENTITY_SECRETE") ?: ""
    val publicKey = load.getString("ktor.circle.getPublicKey") ?: ""
    val createWalletSets = load.getString("ktor.circle.createWalletSets") ?: ""
    val createWallet = load.getString("ktor.circle.createWallet") ?: ""
    val transferCrypto = load.getString("ktor.circle.transferCrypto") ?: ""

}