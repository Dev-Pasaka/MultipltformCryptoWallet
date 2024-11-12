package example.com.data.datasource.remote.response.getWalletBalance

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val tokenBalances: List<TokenBalance>
)