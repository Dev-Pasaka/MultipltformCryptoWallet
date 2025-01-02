package org.example.presentation.screens.dashboardScreen

import org.example.domain.model.Transaction

data class TransactionsState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val error: String = "",
)