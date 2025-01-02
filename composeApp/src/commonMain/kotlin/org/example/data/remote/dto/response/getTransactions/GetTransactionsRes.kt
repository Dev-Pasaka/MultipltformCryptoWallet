package org.example.data.remote.dto.response.getTransactions

import kotlinx.serialization.Serializable

@Serializable
data class GetTransactionsRes(
    val `data`: List<Data>,
    val httpStatusCode: Int,
    val message: String,
    val status: Boolean
)