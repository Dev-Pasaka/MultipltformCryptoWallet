package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class GetWalletRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val data: List<WalletRes>? = null

)
