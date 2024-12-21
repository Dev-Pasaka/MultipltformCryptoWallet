package org.example.presentation.screens.dashboardScreen

import org.example.domain.model.Wallet

data class WalletState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val wallets: List<Wallet> = emptyList(),
    val error: String = ""
)
