package org.example.presentation.dto.request

import kotlinx.serialization.Serializable
import org.example.common.utils.generateShortcode
import org.example.domain.entries.RequestLink

@Serializable
data class CreateRequestLinkReq(
    val walletId: String,
    val blockchain: String,
    val address: String,
    val amount: Double,
){
    fun toRequestLink() = RequestLink(
        walletId = walletId,
        blockchain = blockchain,
        address = address,
        amount = amount,
        shortCode = generateShortcode()
    )
}