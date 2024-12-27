package org.example.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoReq(
    val amount: Double,
    val destinationAddress: String,
    val idempotencyKey: String,
    val tokenId: String,
    val walletId: String
)