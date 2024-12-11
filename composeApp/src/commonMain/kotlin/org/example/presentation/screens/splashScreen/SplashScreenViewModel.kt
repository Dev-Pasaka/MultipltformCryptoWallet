package org.example.presentation.screens.splashScreen

import androidx.lifecycle.ViewModel

class SplashScreenViewModel(): ViewModel() {

    fun isUserSessionValid(onNavigateToDashboard: () -> Unit, onNavigateToAuthScreen: () -> Unit) {
        val session = false
        if (session) {
            onNavigateToDashboard()
        } else {
            onNavigateToAuthScreen()
        }
    }
}