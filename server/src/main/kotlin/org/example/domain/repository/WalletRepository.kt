package org.example.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.presentation.dto.response.CreateWalletRes
import org.example.presentation.dto.response.GetWalletBalanceRes
import org.example.presentation.dto.response.GetWalletRes
import org.example.presentation.dto.response.RestoreWalletRes
import org.example.presentation.dto.response.WalletSearchRes

interface WalletRepository {
    suspend fun createWalletSet():Pair<Boolean, String>
    suspend fun createWallet(idempotencyKey:String): CreateWalletRes
    suspend fun getAllWallets(): Unit
    suspend fun getWallet(id: String): GetWalletRes
    suspend fun restoreWallet(recoverCode:String): RestoreWalletRes
    suspend fun searchWallet(searchText:String): Flow<WalletSearchRes>
    suspend fun getWalletBalance(walletId: String): GetWalletBalanceRes
}