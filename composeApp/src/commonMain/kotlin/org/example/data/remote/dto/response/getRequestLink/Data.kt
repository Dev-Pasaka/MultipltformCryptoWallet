package org.example.data.remote.dto.response.getRequestLink

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val address: String,
    val amount: Double,
    val blockchain: String,
    val id: String
)