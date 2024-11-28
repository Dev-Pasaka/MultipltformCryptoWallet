package org.example.domain.usecases.wallet

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.GetWalletBalanceRes

class GetWalletBalanceUseCase(
    private val walletRepository: WalletRepository,
    private val serverConfig: ServerConfig
) {
   suspend operator fun invoke(walletId: String): GetWalletBalanceRes = try {
       walletRepository.getWalletBalance(walletId)
   }catch (e: Exception){
       e.printStackTrace()
       serverConfig.logger.warn("Failed to get wallet balance: ${e.message}")
       GetWalletBalanceRes(
           httpStatusCode = HttpStatusCode.InternalServerError.value,
           message = "An error occurred while getting wallet balance"
       )
   }
}