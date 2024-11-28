package org.example.domain.usecases.transaction

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.common.utils.ServerConfig
import org.example.domain.repository.TransactionRepository
import org.example.presentation.dto.response.PersonChat

class TransactionHistoryUseCase(
    private val transactionRepository: TransactionRepository,
    private val serverConfig: ServerConfig,
) {
    suspend operator fun invoke(walletId: String): List<PersonChat>  = withContext(Dispatchers.IO){
        return@withContext transactionRepository.getGroupedTransactions(walletId)
    }
}