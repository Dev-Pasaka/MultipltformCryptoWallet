package org.example.presentation.screens.dashboardScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.eth_icon
import org.example.domain.model.Wallet
import org.example.presentation.screens.dashboardScreen.components.DashboardScreenMiddleSection
import org.example.presentation.screens.dashboardScreen.components.DashboardScreenUpperSection
import org.example.presentation.screens.dashboardScreen.components.WalletItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashBoardScreen(
    onNavigateToExplorer: (String) -> Unit
) {
    val viewModel:DashboardScreenViewModel = koinViewModel()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column{
            DashboardScreenUpperSection()
            DashboardScreenMiddleSection(
                wallets = viewModel.walletState.wallets,
                onNavigateToExplorer = onNavigateToExplorer
            )
        }
    }
}