package org.example.presentation.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.domain.usecases.transaction.CreateTransactionUseCase
import org.example.domain.usecases.transaction.TransactionHistoryUseCase
import org.example.presentation.dto.request.TransferCryptoReqBody
import org.example.presentation.dto.response.TransactionRes
import org.example.presentation.dto.response.TransferCryptoRes

class TransactionService(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val transactionHistoryUseCase: TransactionHistoryUseCase
) {
    suspend fun createTransaction(body: TransferCryptoReqBody): TransferCryptoRes {
        return createTransactionUseCase(body)
    }
    suspend fun transactionHistory(walletId: String): TransactionRes{
        return transactionHistoryUseCase(walletId)
    }
}