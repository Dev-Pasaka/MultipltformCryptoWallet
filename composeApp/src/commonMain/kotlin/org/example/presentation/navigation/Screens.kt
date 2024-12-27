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
data class QRCodeScanner(val blockchain: String, val tokenId : String) : Screen()

@Serializable
data class Explorer(
    val url: String
): Screen()

@Serializable
data class TransactScreen(
    val address: String,
    val blockchain: String,
    val tokenId: String,
    val amount: String
) :Screen()

