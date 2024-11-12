package example.com.data.datasource.remote.response.getAllWalletSets

import kotlinx.serialization.Serializable

@Serializable
data class WalletSet(
    val createDate: String,
    val custodyType: String,
    val id: String,
    val updateDate: String
)