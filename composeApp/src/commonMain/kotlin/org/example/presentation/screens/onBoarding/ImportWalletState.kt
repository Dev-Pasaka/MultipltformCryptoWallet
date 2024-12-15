package org.example.presentation.screens.onBoarding

data class ImportWalletState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val recoverCode: String? = null
)
