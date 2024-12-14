package org.example.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ImportWalletReq(
    val recoveryCode: String
)