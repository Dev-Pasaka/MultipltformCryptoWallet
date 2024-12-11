package org.example.presentation.screens.onBoarding

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.bitcoin
import cryptowallet.composeapp.generated.resources.compose_multiplatform
import cryptowallet.composeapp.generated.resources.ethereum
import cryptowallet.composeapp.generated.resources.tether
import kotlinx.coroutines.launch
import org.example.domain.KeyValueStorage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

class OnBoardingScreenViewModel(
    private val keyValueStorage: KeyValueStorage
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
}