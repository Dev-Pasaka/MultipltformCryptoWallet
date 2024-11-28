package org.example.common.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import org.example.domain.entries.Transaction

object TransactionChannel {
    private val channel = Channel<Transaction>(Channel.UNLIMITED)

    suspend fun sendTransaction(transaction: Transaction) {
        channel.send(transaction)
    }
    suspend fun receiveTransactions(): Flow<Transaction> = flow {
        channel.consumeAsFlow().collect { transaction ->
            emit(transaction)
        }
    }

    fun close() {
        channel.close()
    }

}