package org.example.data.remote.dto.response.getWalletBalance

import kotlinx.serialization.Serializable

@Serializable
data class TokenBalance(
    val amount: String = "",
    val token: Token? = null,
    val updateDate: String = ""
)