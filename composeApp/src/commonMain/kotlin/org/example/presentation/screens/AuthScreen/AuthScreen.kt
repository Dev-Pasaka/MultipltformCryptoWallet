package org.example.presentation.screens.AuthScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen() {
    val viewModel: AuthScreenViewModel = koinViewModel()

}