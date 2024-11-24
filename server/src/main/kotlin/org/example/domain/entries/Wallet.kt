package org.example.domain.entries

import aws.sdk.kotlin.services.s3.model.Object
import org.bson.types.ObjectId
import org.example.presentation.dto.response.SearchData
import org.example.presentation.dto.response.WalletRes
import org.example.presentation.dto.response.WalletSearchRes

data class Wallet(
    val id: String,
    val walletId: String,
    val userId: String? = null,
    val state: String,
    val walletSetId: String,
    val custodyType: String,
    val blockchain: String,
    val accountType: String,
    val address: String,
    val updateDate: String,
    val createDate: String,
    val idempotencyKey: String,
    val recoveryCode: String,

    ) {
    fun toGetWalletRes(): WalletRes {
        return WalletRes(
            id = walletId,
            address = address,
            blockchain = blockchain,
            state = state,
            createDate = createDate,
            updateDate = updateDate,
        )
    }

    fun toWalletSearch() = SearchData(
        walletAddress = address,
        blockchain = blockchain
    )


}
