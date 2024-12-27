package org.example.presentation.screens.transferScreen

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import org.example.domain.usecase.wallet.TransferCryptoUseCase
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.data.remote.dto.request.TransferCryptoReq

class TransferScreenViewModel(
    private val transferCryptoUseCase: TransferCryptoUseCase
) : ViewModel() {
    var transferState by mutableStateOf(TransferState())
        private set




    fun transferCrypto(
        body: TransferCryptoReq
    ) {
        viewModelScope.launch {

            transferCryptoUseCase(body).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        transferState = transferState.copy(
                            isLoading = false,
                            isSuccess = true,
                            message = result.message ?: "Success"
                        )
                        println(result.message)
                    }

                    is Resource.Error -> {
                        transferState = transferState.copy(
                            isLoading = false,
                            isSuccess = false,
                            message = result.message ?: "Error"
                        )
                        println(result.message)

                    }

                    is Resource.Loading -> {
                        transferState = transferState.copy(
                            isLoading = true,
                            isSuccess = false,
                            message = ""
                        )
                        println(result.message)

                    }


                }
            }

        }

    }

}