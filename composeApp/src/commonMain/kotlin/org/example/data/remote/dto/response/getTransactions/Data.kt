package org.example.data.remote.dto.response.getTransactions

import kotlinx.serialization.Serializable
import org.example.domain.model.Transaction

@Serializable
data class Data(
    val amount: String,
    val blockchain: String,
    val feeLevel: String,
    val id: String,
    val receiverAddress: String,
    val senderAddress: String,
    val status: String,
    val timestamp: String
){
    fun toTransaction(): Transaction {
        return Transaction(
            amount = amount,
            blockchain = blockchain,
            feeLevel = feeLevel,
            id = id,
            receiverAddress = receiverAddress,
            senderAddress = senderAddress,
            status = status,
            timestamp = timestamp

        )
    }
}