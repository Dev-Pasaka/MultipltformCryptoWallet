package org.example.presentation.screens.dashboardScreen

import org.example.domain.model.RequestLinkData

data class GetRequestLinkDataState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val data: RequestLinkData? = null,
    val error: String = ""
)