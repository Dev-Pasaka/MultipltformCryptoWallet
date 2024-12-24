package org.example.domain.model

data class RequestLinkData(
    val id: String,
    val address: String,
    val blockchain: String,
    val amount: Double
)
