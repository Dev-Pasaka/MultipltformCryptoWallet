package org.example.presentation.screens.dashboardScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashBoardScreen() {
    val viewModel:DashboardScreenViewModel = koinViewModel()
    viewModel.walletState.wallets.forEach {
        Text(text = it.toString())
    }
}