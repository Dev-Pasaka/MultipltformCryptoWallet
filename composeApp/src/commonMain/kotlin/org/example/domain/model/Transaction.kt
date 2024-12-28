package org.example.domain.model

data class Transaction(
    val amount: String,
    val blockchain: String,
    val feeLevel: String,
    val id: String,
    val receiverAddress: String,
    val senderAddress: String,
    val status: String,
    val timestamp: String,
    val transactionType: String = ""
)