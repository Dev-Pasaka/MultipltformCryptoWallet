package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoReq(
    val walletId: String,
    val tokenId: String,
    val idempotencyKey: String,
    val amount: Double,
    val destinationAddress: String,
)
