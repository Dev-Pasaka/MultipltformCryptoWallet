package org.example.presentation.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionsRes(
    val httpStatusCode: Int,
    val status: Boolean,
    val message: String,
    val data: List<PersonChat>? = emptyList() // Grouped by individual person
)

@Serializable
data class PersonChat(
    val personId: String, // The ID of the person involved in the chat
    val personAddress: String, // The address of the person
    val transactions: List<TransactionChat> // All transactions with this person
)

@Serializable
data class TransactionChat(
    val id: String,
    val senderId: String,
    val senderAddress: String,
    val receiverAddress: String,
    val amount: String,
    val feeLevel: String,
    val blockchain: String,
    val status: String,
    val timestamp: String,
    val message: String
)

