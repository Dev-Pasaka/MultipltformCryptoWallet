package org.example.domain.usecase.wallet

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.common.Constants
import org.example.common.Resource
import org.example.common.generateUUID
import org.example.data.remote.dto.request.TransferCryptoReq
import org.example.data.remote.dto.response.transferCrypto.TransferCryptoRes
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.WalletRepository

class TransferCryptoUseCase(
    private val repository: WalletRepository,
    private val keyValueStorage: KeyValueStorage
) {
    operator fun invoke(
        body: TransferCryptoReq
    ): Flow<Resource<TransferCryptoRes>> = flow {
        emit(Resource.Loading())
        try {
            val walletId = keyValueStorage.getString(Constants.WALLET_ID) ?: ""

            val data = body.copy(
                walletId =walletId,
                idempotencyKey = generateUUID()
            )

            val response = repository.transferCrypto(data)
            println("Transfer response: $response")
            if (response.status){
                emit(Resource.Success(data = response, message = "Success"))
            }else{
                emit(Resource.Error(response.message))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
        }
    }
}