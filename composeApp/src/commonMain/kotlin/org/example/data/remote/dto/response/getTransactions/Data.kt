package org.example.data.remote.dto.response.getTransactions

import kotlinx.serialization.Serializable
import org.example.domain.model.Transaction

@Serializable
data class Data(
    val amount: String,
    val blockchain: String,
    val feeLevel: String,
    val id: String,
    val destinationAddress: String,
    val sourceAddress: String,
    val status: String,
    val timestamp: String,
    val transactionType: String
){
    fun toTransaction(): Transaction {
        return Transaction(
            amount = amount,
            blockchain = blockchain,
            feeLevel = feeLevel,
            id = id,
            receiverAddress = destinationAddress,
            senderAddress = sourceAddress,
            status = status,
            timestamp = timestamp,
            transactionType = when(transactionType){
                "INBOUND" -> "IN"
                "OUTBOUND" -> "OUT"
                else -> ""
            }

        )
    }
}