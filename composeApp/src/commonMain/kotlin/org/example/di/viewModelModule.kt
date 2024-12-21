package org.example.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.presentation.screens.AuthScreen.AuthScreenViewModel
import org.example.presentation.screens.dashboardScreen.DashboardScreenViewModel
import org.example.presentation.screens.onBoarding.OnBoardingScreenViewModel
import org.example.presentation.screens.splashScreen.SplashScreenViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    single{SplashScreenViewModel()}
    single{AuthScreenViewModel()}
    single{OnBoardingScreenViewModel(get(), get())}
    single{DashboardScreenViewModel(get())}
}