package org.example.domain.usecases.wallet

import org.example.common.utils.ServerConfig
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.RestoreWalletRes

class RestoreWalletUseCase(
    private val walletRepository: WalletRepository,
    private val severConfig: ServerConfig
) {
    suspend operator fun invoke(recoverCode: String): RestoreWalletRes  = try{
       walletRepository.restoreWallet(recoverCode)
    }catch (e: Exception){
        severConfig.logger.warn("Failed to restore wallet: ${e.message}")
        RestoreWalletRes(
            httpStatusCode = 500,
            status = false,
            message = e.message ?: "Unknown error",
        )
    }

}