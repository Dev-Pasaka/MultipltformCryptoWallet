package org.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.presentation.screens.AuthScreen.AuthScreen
import org.example.presentation.screens.dashboardScreen.DashBoardScreen
import org.example.presentation.screens.explorer.ExplorerScreen
import org.example.presentation.screens.onBoarding.OnBoardingScreen
import org.example.presentation.screens.splashScreen.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.DashBoard
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
                }
            )
        }
        composable<Screen.AuthScreen> {
            AuthScreen()
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
    }
}