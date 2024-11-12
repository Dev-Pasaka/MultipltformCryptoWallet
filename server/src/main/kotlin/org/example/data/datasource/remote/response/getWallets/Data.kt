package example.com.data.datasource.remote.response.getWallets

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val wallets: List<Wallet>
)