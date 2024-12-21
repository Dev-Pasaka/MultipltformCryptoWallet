package org.example.data.remote.dto.response.createWallet

import kotlinx.serialization.Serializable
import org.example.domain.model.WalletSecrete

@Serializable
data class CreateWalletRes(
    val httpStatusCode: Int,
    val message: String,
    val secrets: Secrets? = null,
    val status: Boolean,
    val wallet: List<Wallet>
){
    fun toWalletSecret() = WalletSecrete(
        id = secrets?.id ?: "",
        recoveryCode = secrets?.recoverCode ?: ""
    )

}