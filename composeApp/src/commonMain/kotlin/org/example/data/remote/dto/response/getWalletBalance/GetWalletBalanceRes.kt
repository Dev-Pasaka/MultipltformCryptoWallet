package org.example.data.remote.dto.response.getWalletBalance

import kotlinx.serialization.Serializable
import org.example.domain.model.WalletBalance

@Serializable
data class GetWalletBalanceRes(
    val `data`: Data? = null,
    val httpStatusCode: Int,
    val message: String,
    val status: Boolean
){
    fun toWalletBalance(): List<WalletBalance>?{
        return this.data?.data?.tokenBalances?.map {
            WalletBalance(
                amount = it.amount,
                tokenId = it.token?.id ?: "",
                blockchain = it.token?.blockchain ?: ""
            )
        }
    }
}