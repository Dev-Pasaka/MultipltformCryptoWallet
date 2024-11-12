package example.com.data.datasource.remote.response.getWalletSet

import kotlinx.serialization.Serializable

@Serializable
data class WalletSet(
    val createDate: String,
    val custodyType: String,
    val id: String,
    val name:String? = null,
    val updateDate: String
)