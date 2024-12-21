package org.example.data.remote.dto.response.importWallet

import kotlinx.serialization.Serializable
import org.example.domain.model.WalletSecrete

@Serializable
data class ImportWalletRes(
    val `data`: List<Data>?,
    val httpStatusCode: Int,
    val message: String,
    val secrets: Secrets,
    val status: Boolean
){
    fun toWalletSecrete():WalletSecrete {
        println("secrets: $secrets")
        return WalletSecrete(
            id = secrets.id,
            recoverCode = secrets.recoverCode
        )
    }
}