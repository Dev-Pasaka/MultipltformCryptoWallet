package example.com.data.datasource.remote.response.createWalletSets

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val walletSet: WalletSet
)