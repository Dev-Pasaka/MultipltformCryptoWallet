package org.example.presentation.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletSearchRes(
    val status: Boolean = false,
    val message: String = "",
    val username: String? = null,
    val data: List<SearchData>? = null
)

@Serializable
data class SearchData(
    val walletAddress: String,
    val blockchain: String? = null,
)

