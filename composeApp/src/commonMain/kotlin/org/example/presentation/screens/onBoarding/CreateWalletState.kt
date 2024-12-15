package org.example.presentation.screens.onBoarding

data class CreateWalletState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val recoverCode: String? = null,
    val errorMessage: String? = null,
)
