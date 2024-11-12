package example.com.data.datasource.remote.response.getWalletBalance

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val blockchain: String,
    val createDate: String,
    val decimals: Int,
    val id: String,
    val isNative: Boolean,
    val name: String,
    val symbol: String,
    val updateDate: String
)