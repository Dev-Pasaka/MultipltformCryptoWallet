package org.example.data.remote.dto.response.importWallet

import kotlinx.serialization.Serializable

@Serializable
data class Secrets(
    val id: String,
    val recoverCode: String
)