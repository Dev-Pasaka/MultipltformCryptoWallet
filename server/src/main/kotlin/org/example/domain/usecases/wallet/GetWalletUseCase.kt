package org.example.domain.usecases.wallet

import io.ktor.http.HttpStatusCode
import org.example.common.Resource
import org.example.common.utils.ServerConfig
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.GetWalletRes

class GetWalletUseCase(
    private val walletRepository: WalletRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(id:String) = try {
        walletRepository.getWallet(id)
    }catch (e: Exception) {
        serverConfig.logger.warn("GetWalletUseCase: ${e.message}")
        GetWalletRes(
            httpStatusCode = HttpStatusCode.BadRequest.value,
            status = false,
            message = e.message ?: "An error occurred",
            data = null
        )
    }
}