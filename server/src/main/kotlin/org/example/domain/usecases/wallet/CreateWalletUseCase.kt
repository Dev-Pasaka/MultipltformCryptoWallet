package org.example.domain.usecases.wallet

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.CreateWalletRes

class CreateWalletUseCase(
    private val walletRepository: WalletRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(idempotencyKey:String) = try {
        walletRepository.createWallet(idempotencyKey)
    }catch (e: Exception){
        serverConfig.logger.warn("CreateWalletUseCase: ${e.message}")
        CreateWalletRes(
            httpStatusCode = HttpStatusCode.BadRequest.value,
            status = false,
            message = e.message ?: "An error occurred",
            wallet = null
        )
    }

}