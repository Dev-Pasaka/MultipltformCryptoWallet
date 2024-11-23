package org.example.domain.usecases.user

import org.example.common.utils.ServerConfig
import org.example.domain.repository.UserRepository
import org.example.presentation.dto.request.AddAccountToWalletReq
import org.example.presentation.dto.response.AddAccountToWalletRes

class AddAccountToWalletUseCase (
    private val userRepository: UserRepository,
    private val serverConfig: ServerConfig
){
    suspend operator fun invoke(user: AddAccountToWalletReq, walletId: String): AddAccountToWalletRes = try{
        userRepository.addAccountToWallet(user, walletId)
    }catch (e: Exception){
        e.printStackTrace()
        serverConfig.logger.warn("Failed to add account to wallet: ${e.message}")
        AddAccountToWalletRes(
            status = false,
            message = e.message ?: "Something went wrong"
        )
    }

}