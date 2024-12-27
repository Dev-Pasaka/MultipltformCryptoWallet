package org.example.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.presentation.screens.AuthScreen.AuthScreenViewModel
import org.example.presentation.screens.dashboardScreen.DashboardScreenViewModel
import org.example.presentation.screens.onBoarding.OnBoardingScreenViewModel
import org.example.presentation.screens.splashScreen.SplashScreenViewModel
import org.example.presentation.screens.transferScreen.TransferScreenViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    single{SplashScreenViewModel(get())}
    single{AuthScreenViewModel()}
    single{OnBoardingScreenViewModel(get(), get())}
    single{TransferScreenViewModel(get())}
    single{DashboardScreenViewModel(get(), get(), get())}
}