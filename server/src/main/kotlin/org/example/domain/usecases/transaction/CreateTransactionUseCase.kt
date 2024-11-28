package org.example.domain.usecases.transaction

import org.example.common.utils.ServerConfig
import org.example.common.utils.TransactionChannel
import org.example.domain.repository.TransactionRepository
import org.example.presentation.dto.request.TransferCryptoReq
import org.example.presentation.dto.response.TransferCryptoRes

class CreateTransactionUseCase(
    private val repository: TransactionRepository,
    private val transactionChannel: TransactionChannel,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(body: TransferCryptoReq): TransferCryptoRes = try{
        val response = repository.createTransaction(body)
        response.second?.let {
            transactionChannel.sendTransaction(it)
        }
        response.first
    }catch (e: Exception){
        e.printStackTrace()
        serverConfig.logger.warn("Failed to create transaction: ${e.message}")
        TransferCryptoRes(
            httpStatusCode = 400,
            status = false,
            message = "Failed to create transaction"
        )
    }
}