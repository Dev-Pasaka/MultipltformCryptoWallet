package org.example.data.datasource.remote.response.createWalletSets


import example.com.data.datasource.remote.response.createWalletSets.Data
import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletSetsRes(
    val data: Data? = null,
){
//    fun toWalletSetRes() = WalletSet(
//        id = data?.walletSet?.id ?: "",
//        name = data?.walletSet?.name ?: "",
//        custodyType = data?.walletSet?.custodyType ?: "",
//        updateDate = data?.walletSet?.updateDate ?: "",
//        createDate = data?.walletSet?.createDate ?: ""
//    )
//
//    fun toWalletSet() = WalletSet(
//        id = data?.walletSet?.id ?: "",
//        name = data?.walletSet?.name ?: "",
//        custodyType = data?.walletSet?.custodyType ?: "",
//        updateDate = data?.walletSet?.updateDate ?: "",
//        createDate = data?.walletSet?.createDate ?: "",
//        idempotencyKey =  ""
//    )
}

