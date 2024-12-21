package org.example.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.presentation.screens.AuthScreen.AuthScreen
import org.example.presentation.screens.dashboardScreen.DashBoardScreen
import org.example.presentation.screens.onBoarding.OnBoardingScreen
import org.example.presentation.screens.splashScreen.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.DashBoard
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
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                DashBoardScreen()
            }
        }
        composable<Screen.AuthScreen> {
            AuthScreen()
        }
        composable<Screen.OnBoarding> {
            OnBoardingScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.DashBoard){
                        popUpTo(Screen.OnBoarding){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}