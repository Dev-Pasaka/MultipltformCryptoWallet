package example.com.data.datasource.remote.response.getWalletSet

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val walletSet: WalletSet
)