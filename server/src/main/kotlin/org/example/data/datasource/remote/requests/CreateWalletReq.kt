package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletReq(
    val blockchains: List<String> = listOf(

    ),
    val count: Int = 1,
    val entitySecretCipherText: String,
    val idempotencyKey: String,
    val walletSetId: String
)