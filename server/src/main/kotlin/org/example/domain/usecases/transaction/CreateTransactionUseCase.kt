package org.example.domain.usecases.transaction

import org.example.data.datasource.remote.requests.TransferCryptoReq
import org.example.common.utils.ServerConfig
import org.example.domain.repository.CircleRepository
import org.example.presentation.dto.request.TransferCryptoReqBody
import org.example.presentation.dto.response.TransferCryptoRes

class CreateTransactionUseCase(
    private val circleRepository: CircleRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(body: TransferCryptoReqBody): TransferCryptoRes = try {

        serverConfig.logger.info("Received transaction: $body")

        val result = circleRepository.transferCrypto(
            body = TransferCryptoReq(
                amounts = listOf(body.amount.toString()),
                destinationAddress = body.destinationAddress,
                idempotencyKey = body.idempotencyKey,
                walletId = body.walletId,
                tokenId = body.tokenId,
                feeLevel = "HIGH"
            )
        )
        if (result.data != null) {
            TransferCryptoRes(
                httpStatusCode = 200,
                status = true,
                message = "Transaction created successfully",
            )
        } else {
            serverConfig.logger.warn("Transaction failed: ${result}")
            TransferCryptoRes(
                httpStatusCode = 400,
                status = false,
                message = "Transaction failed",
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        serverConfig.logger.warn("Transaction failed exception : ${e.message}")
        TransferCryptoRes(
            httpStatusCode = 400,
            status = false,
            message = "Transaction failed"
        )

    }
}