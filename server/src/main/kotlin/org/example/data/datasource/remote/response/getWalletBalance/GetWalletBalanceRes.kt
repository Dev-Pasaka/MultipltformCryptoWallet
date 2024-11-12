package example.com.data.datasource.remote.response.getWalletBalance

import kotlinx.serialization.Serializable

@Serializable
data class GetWalletBalanceRes(
    val data:Data
)