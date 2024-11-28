package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoReq(
    val amounts: List<String>,
    val destinationAddress: String,
    val entitySecretCipherText: String = "",
    val idempotencyKey: String,
    val feeLevel: String = "HIGH",
    val walletId: String,
    val tokenId:String = "",
)