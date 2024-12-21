package org.example.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.ethereum
import cryptowallet.composeapp.generated.resources.tether
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.common.generateUUID
import org.example.domain.usecase.wallet.CreateWalletUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.example.domain.usecase.wallet.ImportWalletUseCase

class OnBoardingScreenViewModel(
    private val createWalletUseCase: CreateWalletUseCase,
    private val importWalletUseCase: ImportWalletUseCase
): ViewModel() {

    var createWalletState by mutableStateOf(CreateWalletState())
        private set
    var importWalletState by mutableStateOf(ImportWalletState())
        private set

    val onBoardingData  = mutableStateListOf(
        OnBoardingData(
            title = "Lets get started",
            description = "Making crypto simple for everyone",
            image = Res.drawable.bitcoin
        ),
        OnBoardingData(
            title = "Secure and instant",
            description = "Reduce waiting time with super fast transactions.",
            image = Res.drawable.ethereum
        ),
        OnBoardingData(
            title = "Crypto address too long?",
            description = "Replace your wallet address with your username or email",
            image = Res.drawable.tether
        ),
    )


    fun createWallet() {
        val uuid = generateUUID()
        viewModelScope.launch {
            createWalletUseCase(uuid).collect{result ->
                when(result){
                    is Resource.Error -> {
                        createWalletState = createWalletState.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = result.message
                            )
                    }
                    is Resource.Loading -> {
                        createWalletState = createWalletState.copy(
                            isLoading = true,
                            isSuccess = false,
                            errorMessage = null
                        )
                        println("Loading")
                    }

                    is Resource.Success -> {
                        createWalletState = createWalletState.copy(
                            isLoading = false,
                            isSuccess = true,
                            errorMessage = null,
                            recoverCode = result.data?.recoveryCode
                        )
                        println("Wallet: " + result.data)
                    }
                }
            }
        }

    }

    fun importWallet(recoverCode: String){
        println("RecoveryCode: $recoverCode")
        viewModelScope.launch {
            importWalletUseCase(recoverCode).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        importWalletState = importWalletState.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = result.message,
                            recoverCode = null
                        )
                        println("Error:" + result.message)
                    }
                    is Resource.Loading -> {
                        importWalletState = importWalletState.copy(
                            isLoading = true,
                            isSuccess = false,
                            errorMessage = null,
                            recoverCode = null
                        )
                        println("Loading")
                    }
                    is Resource.Success -> {
                        importWalletState = importWalletState.copy(
                            isLoading = false,
                            isSuccess = true,
                            errorMessage = null,
                            recoverCode = result.data?.recoveryCode
                        )
                        println("Wallet Recoverycode: " + result.data)
                    }
                }
            }
        }

    }
}