package org.example.data.datasource.remote.response.createWalletRes

import example.com.data.datasource.remote.response.createWalletRes.Wallet
import kotlinx.serialization.Serializable
import org.example.presentation.dto.response.CreateWalletRes
import org.example.presentation.dto.response.WalletRes

@Serializable
data class Data(
    val wallets: List<Wallet>
){
    fun toCreateWalletRes(): List<WalletRes>{
        return wallets.map { wallet ->
            WalletRes(
                id = wallet.id,
                address = wallet.address,
                blockchain = wallet.blockchain,
                state = wallet.state,
                createDate = wallet.createDate,
                updateDate = wallet.updateDate,
            )
        }
    }
}