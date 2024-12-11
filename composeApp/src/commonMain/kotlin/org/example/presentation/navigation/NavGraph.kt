package org.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.presentation.screens.AuthScreen.AuthScreen
import org.example.presentation.screens.onBoarding.OnBoardingScreen
import org.example.presentation.screens.splashScreen.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.OnBoarding
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    )  {
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
        composable<Screen.DashBoard>{

        }
        composable<Screen.AuthScreen> {
            AuthScreen()
        }
        composable<Screen.OnBoarding> {
            OnBoardingScreen()
        }
    }
}