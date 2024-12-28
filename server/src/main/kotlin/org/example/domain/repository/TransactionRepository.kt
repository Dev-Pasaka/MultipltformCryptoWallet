package org.example.domain.repository

import org.example.domain.entries.Transaction
import org.example.domain.entries.TransactionStatus
import org.example.presentation.dto.request.TransferCryptoReq
import org.example.presentation.dto.response.TransactionRes
import org.example.presentation.dto.response.TransferCryptoRes

interface TransactionRepository {
    suspend fun createTransaction(body: TransferCryptoReq): Pair<TransferCryptoRes, Transaction?>
    suspend fun updateTransactionStatus(id: String, status: TransactionStatus): Boolean
    suspend fun getTransactions(walletId: String): TransactionRes
}