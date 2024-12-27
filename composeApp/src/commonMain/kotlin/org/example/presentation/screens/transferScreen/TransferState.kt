package org.example.presentation.screens.transferScreen

data class TransferState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String = "",
)
