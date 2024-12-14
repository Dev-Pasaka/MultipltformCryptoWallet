package org.example.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.ethereum
import cryptowallet.composeapp.generated.resources.tether
import org.example.domain.repository.KeyValueStorage
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.common.Resource
import org.example.domain.usecase.wallet.CreateWalletUseCase

class OnBoardingScreenViewModel(
    private val keyValueStorage: KeyValueStorage,
    private val createWalletUseCase: CreateWalletUseCase
): ViewModel() {
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

    init {

        viewModelScope.launch {
            createWalletUseCase("99404ae7-8363-4c47-87f3-be05c9d779c3").collect{
                when(it){
                    is Resource.Error -> {
                        println(it.message)
                    }
                    is Resource.Loading -> {
                        println(it.message)
                    }
                    is Resource.Success -> {
                        println(it.data)
                    }
                }
            }
        }

    }
}