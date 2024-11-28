package org.example.presentation.service

import org.example.domain.usecases.wallet.CreateWalletUseCase
import org.example.domain.usecases.wallet.GetWalletBalanceUseCase
import org.example.domain.usecases.wallet.GetWalletUseCase
import org.example.domain.usecases.wallet.RestoreWalletUseCase
import org.example.domain.usecases.wallet.SearchWalletUseCase

class WalletService(
    private val createWalletUseCase: CreateWalletUseCase,
    private val getWalletsUseCase: GetWalletUseCase,
    private val restoreWalletUseCase: RestoreWalletUseCase,
    private val searchWalletUseCase: SearchWalletUseCase,
    private val getWalletBalanceUseCase: GetWalletBalanceUseCase

) {
    suspend fun createWallet(idempotencyKey:String) = createWalletUseCase(idempotencyKey)
    suspend fun getWallet(id:String) = getWalletsUseCase(id)
    suspend fun restoreWallet(recoverCode:String) = restoreWalletUseCase(recoverCode)
    suspend fun searchWallet(searchText:String) = searchWalletUseCase(searchText)
    suspend fun getWalletBalance(walletId:String) = getWalletBalanceUseCase(walletId)

}