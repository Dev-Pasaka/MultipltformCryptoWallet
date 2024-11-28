package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import org.example.domain.entries.Transaction

@Serializable
data class TransferCryptoRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
)
