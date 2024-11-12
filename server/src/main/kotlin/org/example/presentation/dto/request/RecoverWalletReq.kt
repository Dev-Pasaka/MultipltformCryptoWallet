package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RecoverWalletReq(
    val recoveryCode: String,
)
