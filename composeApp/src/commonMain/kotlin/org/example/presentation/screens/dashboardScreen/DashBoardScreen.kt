package org.example.presentation.screens.dashboardScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashBoardScreen() {
    val viewModel:DashboardScreenViewModel = koinViewModel()
    Column {
        viewModel.walletState.wallets.forEach {
            Text(text = "${it.address} ${it.blockchain} ${it.walletBalance?.amount}")
        }
    }
}