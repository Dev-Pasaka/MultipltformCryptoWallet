package org.example.presentation.dto.response

import example.com.data.datasource.remote.response.getWalletBalance.GetWalletBalanceRes
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class GetWalletBalanceRes(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status: Boolean = false,
    val message: String = "",
    val data: GetWalletBalanceRes? = null
)
