package org.example.domain.usecase.wallet

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Constants
import org.example.common.Resource
import org.example.data.remote.dto.request.CreateWalletReq
import org.example.data.remote.dto.response.createWallet.CreateWalletRes
import org.example.domain.model.WalletSecrete
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.WalletRepository

class CreateWalletUseCase(
    private val walletRepository: WalletRepository,
    private val keyValueStorage: KeyValueStorage
) {
    operator fun invoke(idempotencyKey: String): Flow<Resource<WalletSecrete>> = flow {
        try {
            emit(Resource.Loading())
            val response = walletRepository.createWallet(
                CreateWalletReq(idempotencyKey)
            )

            if (!response.status) emit(Resource.Error(response.message))
            emit(Resource.Success(response.toWalletSecret(), response.message))
            keyValueStorage.putString(
                Constants.WALLET_ID,
                response.toWalletSecret().id
            )
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.let {
                emit(Resource.Error(it))
            }

        }
    }

}