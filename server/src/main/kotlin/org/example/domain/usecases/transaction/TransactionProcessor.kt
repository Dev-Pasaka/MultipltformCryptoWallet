package org.example.domain.usecases.transaction

import example.com.data.datasource.remote.requests.TransferCryptoReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.common.utils.ServerConfig
import org.example.common.utils.TransactionChannel
import org.example.domain.entries.Transaction
import org.example.domain.entries.TransactionStatus
import org.example.domain.repository.CircleRepository
import org.example.domain.repository.TransactionRepository

class TransactionProcessor(
    private val transactionChannel: TransactionChannel,
    private val transactionRepository: TransactionRepository,
    private val circleRepository: CircleRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        transactionChannel.receiveTransactions().collect{transaction->
            serverConfig.logger.info("Received transaction: $transaction")
            try {
                val result = circleRepository.transferCrypto(
                    body = TransferCryptoReq(
                        feeLevel = transaction.feeLevel,
                        amounts = transaction.amounts,
                        destinationAddress = transaction.destinationAddress,
                        idempotencyKey = transaction.idempotencyKey,
                        walletId = transaction.walletId,
                        tokenId = transaction.tokenId,
                    )
                )
                if (result.data != null) {
                    transactionRepository.updateTransactionStatus(
                        transaction.id, TransactionStatus.COMPLETED)
                } else {
                    transactionRepository.updateTransactionStatus(transaction.id, TransactionStatus.FAILED)
                }
            }catch (e:Exception){
                e.printStackTrace()
                serverConfig.logger.warn("Transaction failed: ${e.message}")
                transactionChannel.sendTransaction(transaction)
            }
        }

    }
}