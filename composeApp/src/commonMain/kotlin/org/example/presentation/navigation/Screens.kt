package org.example.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen() {
    @Serializable
    object Splash : Screen()
    @Serializable
    object DashBoard : Screen()
    @Serializable
    object AuthScreen: Screen()
    @Serializable
    object OnBoarding : Screen()

}

@Serializable
data class Explorer(
    val url: String
): Screen()