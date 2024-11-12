package org.example.presentation.service

import org.example.domain.usecases.wallet.CreateWalletUseCase
import org.example.domain.usecases.wallet.GetWalletUseCase
import org.example.domain.usecases.wallet.RestoreWalletUseCase

class WalletService(
    private val createWalletUseCase: CreateWalletUseCase,
    private val getWalletsUseCase: GetWalletUseCase,
    private val restoreWalletUseCase: RestoreWalletUseCase,
) {
    suspend fun createWallet(idempotencyKey:String) = createWalletUseCase(idempotencyKey)
    suspend fun getWallet(id:String) = getWalletsUseCase(id)
    suspend fun restoreWallet(recoverCode:String) = restoreWalletUseCase(recoverCode)

}