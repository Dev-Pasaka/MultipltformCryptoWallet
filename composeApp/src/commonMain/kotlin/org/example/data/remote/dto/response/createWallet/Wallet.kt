package org.example.data.remote.dto.response.createWallet

import kotlinx.serialization.Serializable

@Serializable
data class Wallet(
    val address: String,
    val blockchain: String,
    val createDate: String,
    val id: String,
    val state: String,
    val updateDate: String
)