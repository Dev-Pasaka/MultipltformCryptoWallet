package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletReq(
    val idempotencyKey:String,
)
