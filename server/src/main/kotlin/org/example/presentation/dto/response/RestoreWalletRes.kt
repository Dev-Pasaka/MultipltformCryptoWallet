package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class RestoreWalletRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val secrets: RestoreWalletSecrete? = null,
    val data: List<WalletRes>? = null

)

@Serializable
data class RestoreWalletSecrete(
    val id: String,
    val recoverCode: String,
)