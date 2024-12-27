package org.example.presentation.screens.dashboardScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.domain.usecase.wallet.GetWalletUseCase
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import org.example.data.remote.dto.request.CreateRequestLinkReq
import org.example.data.remote.dto.request.TransferCryptoReq
import org.example.domain.usecase.requestLink.CreateRequestLinkUseCase
import org.example.domain.usecase.requestLink.GetRequestLinkDataUseCase
import org.example.presentation.screens.dashboardScreen.WalletState

class DashboardScreenViewModel(
    private val getWalletUseCase: GetWalletUseCase,
    private val createRequestLinkUseCase: CreateRequestLinkUseCase,
    private val getRequestLinkDataUseCase: GetRequestLinkDataUseCase
): ViewModel() {

    var walletState by mutableStateOf(WalletState())
        private set
    var createRequestLinkState by mutableStateOf(CreateRequestLinkState())
        private set
    var getRequestLinkDataState by mutableStateOf(GetRequestLinkDataState())
        private set


    fun getWallet(){
        viewModelScope.launch{
            getWalletUseCase().collect{result ->
                when(result){
                    is Resource.Error -> {
                        walletState = walletState.copy(
                            error = result.message.toString(),
                            isSuccessful = false,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        walletState = walletState.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        walletState = walletState.copy(
                            isLoading = false,
                            isSuccessful = true,
                            wallets = result.data ?: emptyList()
                        )
                    }
                }
            }

        }
    }

    fun createRequestLink(
        data:CreateRequestLinkReq
    ){
        viewModelScope.launch {
            createRequestLinkUseCase.invoke(data).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        createRequestLinkState = createRequestLinkState.copy(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        createRequestLinkState = createRequestLinkState.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        createRequestLinkState = createRequestLinkState.copy(
                            isLoading = false,
                            isSuccessful = true,
                            link = result.data ?: ""
                        )
                    }
                }
            }
        }
    }
    fun getRequestLinkData(){
        val shortCode = createRequestLinkState.link.split("/").last()
        viewModelScope.launch{
            getRequestLinkDataUseCase.invoke(shortCode).collect{result ->
                when(result) {
                    is Resource.Error -> {
                        getRequestLinkDataState = getRequestLinkDataState.copy(
                            isLoading = false,
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        getRequestLinkDataState = getRequestLinkDataState.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        getRequestLinkDataState = getRequestLinkDataState.copy(
                            isLoading = false,
                            isSuccessful = true,
                            data = result.data
                        )
                    }
                }
            }
        }
    }

    init {
        getWallet()
    }
}