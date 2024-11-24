package org.example.domain.usecases.wallet

import kotlinx.coroutines.flow.Flow
import org.example.common.utils.ServerConfig
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.WalletSearchRes

class SearchWalletUseCase(
    private val walletRepository: WalletRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(searchText: String): Flow<WalletSearchRes> {
        return walletRepository.searchWallet(searchText)
    }
}