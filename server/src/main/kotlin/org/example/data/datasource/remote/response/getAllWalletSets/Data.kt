package example.com.data.datasource.remote.response.getAllWalletSets

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val walletSets: List<WalletSet>
)