package org.example.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoReq(
    val feeLevel: String,
    val idempotencyKey: String,
    val amounts: List<String>,
    val tokenId: String,
    val walletId: String,
    val destinationAddress: String,
    val entitySecretCipherText: String = "",
)