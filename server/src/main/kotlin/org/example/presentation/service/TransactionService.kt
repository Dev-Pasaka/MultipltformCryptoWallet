package org.example.presentation.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.domain.repository.TransactionRepository
import org.example.domain.usecases.transaction.CreateTransactionUseCase
import org.example.domain.usecases.transaction.TransactionHistoryUseCase
import org.example.domain.usecases.transaction.TransactionProcessor
import org.example.presentation.dto.request.TransferCryptoReq
import org.example.presentation.dto.response.TransactionRes
import org.example.presentation.dto.response.TransferCryptoRes

class TransactionService(
    private val transactionProcessor: TransactionProcessor,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val transactionHistoryUseCase: TransactionHistoryUseCase
) {
    suspend fun createTransaction(body: TransferCryptoReq): TransferCryptoRes {
        return createTransactionUseCase(body)
    }
    suspend fun transactionHistory(walletId: String): TransactionRes{
        return transactionHistoryUseCase(walletId)
    }



    init {
        CoroutineScope(Dispatchers.IO).launch {
            transactionProcessor()
        }
    }
}