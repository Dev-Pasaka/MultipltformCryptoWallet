package org.example.domain.repository

import org.example.data.remote.dto.request.TransferCryptoReq
import org.example.data.remote.dto.request.CreateWalletReq
import org.example.data.remote.dto.request.ImportWalletReq
import org.example.data.remote.dto.response.createWallet.CreateWalletRes
import org.example.data.remote.dto.response.getWallet.GetWalletRes
import org.example.data.remote.dto.response.getWalletBalance.GetWalletBalanceRes
import org.example.data.remote.dto.response.importWallet.ImportWalletRes
import org.example.data.remote.dto.response.transferCrypto.TransferCryptoRes

interface WalletRepository {
    suspend fun createWallet(body: CreateWalletReq): CreateWalletRes
    suspend fun importWallet(body: ImportWalletReq): ImportWalletRes
    suspend fun getWallet(id: String):GetWalletRes
    suspend fun getWalletBalance(walletId: String): GetWalletBalanceRes
    suspend fun transferCrypto(body: TransferCryptoReq): TransferCryptoRes
}


