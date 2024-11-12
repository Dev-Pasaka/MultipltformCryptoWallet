package example.com.data.datasource.remote.response.updateWalletSet

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val walletSet: WalletSet
)