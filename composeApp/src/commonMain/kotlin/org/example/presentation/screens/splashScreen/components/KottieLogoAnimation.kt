package org.example.presentation.screens.splashScreen.components

import KottieAnimation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kottieAnimationState.KottieAnimationState

@Composable
fun KottieLogoAnimation(
    composition: Any?,
    animationState: KottieAnimationState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(

        ){
            KottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = modifier.size(300.dp)
            )
        }

    }
}