package org.example.presentation.screens.dashboardScreen

data class CreateRequestLinkState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val link: String = "",
    val error: String = ""
)
