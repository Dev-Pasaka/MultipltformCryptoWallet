package org.example.presentation.screens.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.domain.usecase.user.GetSessionStatusUseCase

class SplashScreenViewModel(
    private val getSessionStatusUseCase: GetSessionStatusUseCase
) : ViewModel() {

    fun isUserSessionValid(onNavigateToDashboard: () -> Unit, onNavigateToAuthScreen: () -> Unit) {
        viewModelScope.launch {
            val isSessionValid = getSessionStatusUseCase()
            if (isSessionValid) {
                onNavigateToDashboard()
            } else {
                onNavigateToAuthScreen()
            }
        }

    }
}