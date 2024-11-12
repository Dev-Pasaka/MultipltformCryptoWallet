package example.com.data.datasource.remote.response.getWalletBalance

import kotlinx.serialization.Serializable

@Serializable
data class TokenBalance(
    val amount: String,
    val token: Token,
    val updateDate: String
)