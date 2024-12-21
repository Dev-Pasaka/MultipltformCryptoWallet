package org.example.data.remote.dto.response.getWallet

import cryptowallet.composeapp.generated.resources.Res
import cryptowallet.composeapp.generated.resources.eth_icon
import cryptowallet.composeapp.generated.resources.ethereum
import cryptowallet.composeapp.generated.resources.matic_icon
import cryptowallet.composeapp.generated.resources.sol_icon
import kotlinx.serialization.Serializable
import org.example.domain.model.Wallet

@Serializable
data class GetWalletRes(
    val `data`: List<Data>? = null,
    val httpStatusCode: Int,
    val message: String,
    val status: Boolean
){
    fun toWallet(): List<Wallet>?{
        return this.data?.map {
            Wallet(
                individualWalletId = it.id,
                address = it.address,
                blockchain = it.blockchain,
                state = it.state,
                createDate = it.createDate,
                updateDate = it.updateDate,
                icon = when(it.blockchain){
                    "ETH-SEPOLIA" -> Res.drawable.eth_icon
                    "MATIC-AMOY" -> Res.drawable.matic_icon
                    "SOL-DEVNET" -> Res.drawable.sol_icon
                    else -> Res.drawable.ethereum
                }
            )
        }
    }
}