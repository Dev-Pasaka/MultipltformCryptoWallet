package org.example.domain.usecase.wallet

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Constants
import org.example.common.Resource
import org.example.domain.model.Transaction
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.WalletRepository

class GetTransactionUseCase(
    private val repository: WalletRepository,
    private val keyValueStorage: KeyValueStorage
) {
    suspend operator fun invoke(): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading())
        try {
            val walletId = keyValueStorage.getString(Constants.WALLET_ID) ?: ""
            val transactions = repository.getTransactions(walletId)
            if (transactions.status) {
                emit(
                    Resource.Success(
                        data = transactions.data.map { it.toTransaction() },
                        message = "Transactions retrieved successfully"
                    )
                )
            } else {
                emit(Resource.Error(transactions.message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}