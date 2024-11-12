package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val secrets: SecretsRes?= null,
    val wallet: List<WalletRes>? = null
)
@Serializable
data class SecretsRes(
    val id: String,
    val recoverCode: String,
)


