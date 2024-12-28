package org.example.domain.entries

import org.bson.types.ObjectId
import org.example.common.utils.TimeUtils
import org.example.presentation.dto.response.TransactionData

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
){
    fun toTransaction() = TransactionData(
        id = id,
        amount = amounts.first(),
        senderAddress = senderAddress,
        receiverAddress = destinationAddress,
        feeLevel = feeLevel,
        blockchain = blockchain,
        status = status.name,
        timestamp = timestamp
    )
}

enum class TransactionStatus{
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
}
