package org.example.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class CancelTransactionReq(
    val entitySecretCiphertext: String,
    val idempotencyKey: String,
)
