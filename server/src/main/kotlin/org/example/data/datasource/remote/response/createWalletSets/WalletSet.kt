package example.com.data.datasource.remote.response.createWalletSets

import kotlinx.serialization.Serializable

@Serializable
data class WalletSet(
    val createDate: String,
    val custodyType: String,
    val id: String,
    val name: String,
    val updateDate: String
)