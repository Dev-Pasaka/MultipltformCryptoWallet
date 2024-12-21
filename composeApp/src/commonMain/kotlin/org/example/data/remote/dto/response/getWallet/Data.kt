package org.example.data.remote.dto.response.getWallet

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val address: String,
    val blockchain: String,
    val createDate: String,
    val id: String,
    val state: String,
    val updateDate: String
)