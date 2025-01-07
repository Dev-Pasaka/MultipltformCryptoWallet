package org.example.presentation.screens.transferScreen

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import org.example.domain.usecase.wallet.TransferCryptoUseCase
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.data.remote.dto.request.TransferCryptoReq

class TransferScreenViewModel(
    private val transferCryptoUseCase: TransferCryptoUseCase
) : ViewModel() {
    var transferState by mutableStateOf(TransferState())
        private set


    private val _transferringEvent = MutableSharedFlow<DialogBoxEvents>()
    val transferringEvent = _transferringEvent.asSharedFlow()


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
                        _transferringEvent.emit(
                            DialogBoxEvents(
                                status = "Success",
                                message = result.message ?: "Success"
                            )
                        )
                    }

                    is Resource.Error -> {
                        transferState = transferState.copy(
                            isLoading = false,
                            isSuccess = false,
                            message = result.message ?: "Error"
                        )
                        println(result.message)
                        _transferringEvent.emit(
                            DialogBoxEvents(
                                status = "Error",
                                message = result.message ?: "An expected error occurred"
                            )
                        )

                    }

                    is Resource.Loading -> {
                        transferState = transferState.copy(
                            isLoading = true,
                            isSuccess = false,
                            message = ""
                        )
                        println(result.message)
                        _transferringEvent.emit(
                            DialogBoxEvents(
                                status = "Transferring",
                                message = "Transferring..."
                            )
                        )

                    }


                }
            }

        }

    }

}