package org.example.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestLinkReq(
    val address: String,
    val amount: Double,
    val blockchain: String,
    val walletId: String
)