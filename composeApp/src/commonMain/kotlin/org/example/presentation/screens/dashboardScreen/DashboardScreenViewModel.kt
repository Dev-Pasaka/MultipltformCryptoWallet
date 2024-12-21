package org.example.presentation.screens.dashboardScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.domain.usecase.wallet.GetWalletUseCase
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import org.example.presentation.screens.dashboardScreen.WalletState

class DashboardScreenViewModel(
    private val getWalletUseCase: GetWalletUseCase
): ViewModel() {

    var walletState by mutableStateOf(WalletState())
        private set

    fun getWallet(){
        viewModelScope.launch{
            getWalletUseCase().collect{result ->
                when(result){
                    is Resource.Error -> {
                        walletState = walletState.copy(
                            error = result.message.toString()
                        )
                    }
                    is Resource.Loading -> {
                        walletState = walletState.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        walletState = walletState.copy(
                            isSuccessful = true,
                            wallets = result.data ?: emptyList()
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