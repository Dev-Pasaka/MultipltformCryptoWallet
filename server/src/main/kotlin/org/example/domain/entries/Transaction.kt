package org.example.domain.entries

import org.bson.types.ObjectId
import org.example.common.utils.TimeUtils

data class Transaction(
    val id: String = ObjectId().toString(),
    val senderId: String,
    val idempotencyKey: String,
    val amounts: List<String>,
    val senderAddress: String,
    val destinationAddress: String,
    val feeLevel: String = "MEDIUM",
    val walletId: String,
    val tokenId:String = "",
    val blockchain: String,
    val status: TransactionStatus = TransactionStatus.PENDING,
    val timestamp: String = TimeUtils.getCurrentUtcTimestamp().toString()
)

enum class TransactionStatus{
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}
