package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val data: Data? = null
)

@Serializable
data class Data(
    val secretsRes: SecretsRes?,
    val wallet: List<WalletRes>? = null
)