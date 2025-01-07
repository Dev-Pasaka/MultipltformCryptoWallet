package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoReqBody(
    val walletId: String,
    val tokenId: String,
    val idempotencyKey: String,
    val amount: Double,
    val destinationAddress: String,
)
