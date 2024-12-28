package org.example.domain.usecase.wallet

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Constants
import org.example.common.Resource
import org.example.domain.model.Wallet
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.WalletRepository

class GetWalletUseCase(
    private val walletRepository: WalletRepository,
    private val keyValueStorage: KeyValueStorage
) {
    operator fun invoke(): Flow<Resource<List<Wallet>>> = flow {
        emit(Resource.Loading())
        try {
            val walletId = keyValueStorage.getString(Constants.WALLET_ID) ?: ""
            var wallets = walletRepository.getWallet(walletId).toWallet()
            println("wallets: $wallets")
            val balances= walletRepository.getWalletBalance(walletId).toWalletBalance()

            // Add balances to wallets
            val result = wallets?.map { wallet ->
                val matchingBalance = balances?.find { it.blockchain == wallet.blockchain }
                wallet.copy(walletBalance = matchingBalance)
            } ?: emptyList()
            emit(Resource.Success(data = result, message = "Success"))
        }catch (e:Exception){
            e.printStackTrace()
            emit(Resource.Error(message = e.message.toString()))
        }

    }
}