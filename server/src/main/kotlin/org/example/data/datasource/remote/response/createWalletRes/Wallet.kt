package example.com.data.datasource.remote.response.createWalletRes

import kotlinx.serialization.Serializable

@Serializable
data class Wallet(
    val accountType: String,
    val address: String,
    val blockchain: String,
    val createDate: String,
    val custodyType: String,
    val id: String,
    val state: String,
    val updateDate: String,
    val walletSetId: String
)