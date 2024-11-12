package org.example.domain.repository

import org.example.presentation.dto.response.CreateWalletRes
import org.example.presentation.dto.response.GetWalletRes
import org.example.presentation.dto.response.RestoreWalletRes

interface WalletRepository {
    suspend fun createWalletSet():Pair<Boolean, String>
    suspend fun createWallet(idempotencyKey:String): CreateWalletRes
    suspend fun getAllWallets(): Unit
    suspend fun getWallet(id: String): GetWalletRes
    suspend fun restoreWallet(recoverCode:String): RestoreWalletRes
}