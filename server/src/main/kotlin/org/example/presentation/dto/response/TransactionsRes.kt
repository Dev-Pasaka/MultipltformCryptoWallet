package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val data: List<TransactionData>? = null
)

@Serializable
data class TransactionData(
    val id: String,
    val senderAddress: String,
    val receiverAddress: String,
    val amount: String,
    val feeLevel: String,
    val blockchain: String,
    val status: String,
    val timestamp: String,
)

