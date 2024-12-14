package org.example.domain.repository

import org.example.data.remote.dto.request.CreateWalletReq
import org.example.data.remote.dto.request.ImportWalletReq
import org.example.data.remote.dto.response.createWallet.CreateWalletRes
import org.example.data.remote.dto.response.importWallet.ImportWalletRes

interface WalletRepository {
    suspend fun createWallet(body: CreateWalletReq): CreateWalletRes
    suspend fun importWallet(body: ImportWalletReq): ImportWalletRes
}


