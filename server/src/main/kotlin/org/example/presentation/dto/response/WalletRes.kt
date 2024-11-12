package org.example.presentation.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletRes(
    val id: String,
    val address: String,
    val blockchain: String,
    val state: String,
    val createDate: String,
    val updateDate: String,
)