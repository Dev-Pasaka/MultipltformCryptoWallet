package org.example.data.remote.dto.response.importWallet

import kotlinx.serialization.Serializable

@Serializable
data class ImportWalletRes(
    val `data`: List<Data>,
    val httpStatusCode: Int,
    val message: String,
    val secrets: Secrets,
    val status: Boolean
)