package example.com.data.datasource.remote.response.getWallets

import kotlinx.serialization.Serializable

@Serializable
data class Wallet(
    val accountType: String,
    val address: String,
    val blockchain: String,
    val createDate: String,
    val custodyType: String,
    val id: String,
    val name: String? = null,
    val refId: String? = null,
    val state: String,
    val updateDate: String,
    val userId: String? = null,
    val walletSetId: String
)