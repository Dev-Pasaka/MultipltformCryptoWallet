package org.example.domain.usecase.wallet

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Constants
import org.example.common.Resource
import org.example.data.remote.dto.request.ImportWalletReq
import org.example.domain.model.WalletSecrete
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.WalletRepository

class ImportWalletUseCase(
    private val keyValueStorage: KeyValueStorage,
    private val walletRepository: WalletRepository
) {
    operator fun invoke(recoverCode: String): Flow<Resource<WalletSecrete>> = flow {
        emit(Resource.Loading())
        try {
            val response = walletRepository.importWallet(
                body = ImportWalletReq(recoveryCode = recoverCode)
            )
            if (response.status == false) emit(Resource.Error(response.message))

            emit(
                Resource.Success(
                    data = response.toWalletSecrete(),
                    message = response.message
                )
            )
            keyValueStorage.putString(
                key = Constants.WALLET_ID,
                value = response.toWalletSecrete().id
            )

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}