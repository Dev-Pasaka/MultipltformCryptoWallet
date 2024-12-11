package org.example.presentation.screens.splashScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import cryptowallet.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.example.presentation.screens.splashScreen.components.KottieLogoAnimation


import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToAuthScreen: () -> Unit,
) {
    val viewModel: SplashScreenViewModel = koinViewModel()

    var animation by remember { mutableStateOf("") }
    var playing by remember { mutableStateOf(true) }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation)
    )

    val animationState by animateKottieCompositionAsState(
        speed = 0.25f,
        composition = composition,
        isPlaying = playing,
        iterations = 1000
    )



    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/logo_animation.json").decodeToString()
        delay(3000)
        viewModel.isUserSessionValid(
            onNavigateToDashboard = onNavigateToDashboard,
            onNavigateToAuthScreen = onNavigateToAuthScreen
        )

    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.material3.MaterialTheme.colorScheme.background
    ) {
        KottieLogoAnimation(
            animationState = animationState,
            composition = composition,
            modifier = Modifier.size(300.dp)
        )
    }
}