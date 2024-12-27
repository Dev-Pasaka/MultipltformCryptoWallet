package org.example.data.remote.dto.response.transferCrypto

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class TransferCryptoRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
)
