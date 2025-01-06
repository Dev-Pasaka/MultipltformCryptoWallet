package org.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.presentation.screens.QrCodeScannerScreen
import org.example.presentation.screens.dashboardScreen.DashBoardScreen
import org.example.presentation.screens.explorer.ExplorerScreen
import org.example.presentation.screens.onBoarding.OnBoardingScreen
import org.example.presentation.screens.splashScreen.SplashScreen
import org.example.presentation.screens.transferScreen.TransferScreen
import qrscanner.QrCodeScanner


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Splash
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.DashBoard)
                },
                onNavigateToAuthScreen = {
                    navController.navigate(Screen.OnBoarding)
                }
            )
        }
        composable<Screen.DashBoard> {
            DashBoardScreen(
                onNavigateToExplorer = { url ->
                    navController.navigate(Explorer(url))
                },
                onNavigateToTransfer = { amount, address, blockchain, selectedTokenId ->
                    navController.navigate(
                        TransactScreen(
                            amount = amount.toString(),
                            address = address,
                            blockchain = blockchain,
                            tokenId = selectedTokenId
                        )
                    )
                },
                onOpenQRCodeScreen = {blockchain, tokenId ->
                    navController.navigate(QRCodeScanner(blockchain = blockchain, tokenId = tokenId))
                }
            )
        }
        composable<Screen.OnBoarding> {
            OnBoardingScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.DashBoard) {
                        popUpTo(Screen.OnBoarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Explorer> { backStackEntry ->
            val explorer: Explorer = backStackEntry.toRoute()
            ExplorerScreen(
                url = explorer.url,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<QRCodeScanner> {backStackEntry ->
            val data: QRCodeScanner = backStackEntry.toRoute()

            QrCodeScannerScreen(
                tokenId = data.tokenId,
                blockchain = data.blockchain,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToTransfer = { address, amount, blockchain, tokenId ->
                    navController.navigate(
                        TransactScreen(
                            address = address,
                            amount = amount.toString(),
                            blockchain = blockchain,
                            tokenId = tokenId
                        )
                    )
                }
            )
        }
        composable<TransactScreen> { backStackEntry ->
            val data: TransactScreen = backStackEntry.toRoute()
            TransferScreen(
                tokenId = data.tokenId,
                blockchain = data.blockchain,
                address = data.address,
                amount = data.amount,
                onCancel = {
                    navController.navigate(Screen.DashBoard){
                        popUpTo<Screen.DashBoard>{
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}