package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletSetsReq(
    val entitySecretCipherText: String,
    val idempotencyKey: String,
    val name: String
)