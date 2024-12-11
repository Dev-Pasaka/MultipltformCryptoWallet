package org.example.presentation.screens.onBoarding

import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource

data class OnBoardingData(
    val title: String,
    val description: String,
    val image: DrawableResource
)