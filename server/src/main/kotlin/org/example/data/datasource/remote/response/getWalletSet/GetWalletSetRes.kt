package org.example.data.datasource.remote.response.getWalletSet

import example.com.data.datasource.remote.response.getWalletSet.Data
import kotlinx.serialization.Serializable

@Serializable
data class GetWalletSetRes(
    val data: Data
){
   /* fun toWalletSet() =  WalletSet(
        id = data.walletSet.id,
        name = data.walletSet.name ?: "",
        custodyType = data.walletSet.custodyType,
        createDate = data.walletSet.createDate,
        updateDate = data.walletSet.updateDate
    )*/
}