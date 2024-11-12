package example.com.data.datasource.remote.response.getWallet

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val wallet: Wallet
)