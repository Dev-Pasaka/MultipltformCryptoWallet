package org.example.presentation.dto.response

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class GetRequestLinkRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val data: RequestLinkData? = null
)

@Serializable
data class RequestLinkData(
    val id: String,
    val address: String,
    val blockchain: String,
    val amount: Double
)
