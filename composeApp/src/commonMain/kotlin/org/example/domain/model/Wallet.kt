package org.example.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class Wallet(
    val individualWalletId: String,
    val tokenId:String? = null,
    val address: String,
    val blockchain: String,
    val state: String,
    val icon: DrawableResource,
    val walletBalance: WalletBalance? = null,
    val explorerUrl: String? = null,
    val createDate: String,
    val updateDate: String
)
data class WalletBalance(
    val amount: String,
    val tokenId: String,
    val blockchain: String
)

