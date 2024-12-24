package org.example.domain.entries

import org.bson.types.ObjectId
import org.example.common.utils.TimeUtils
import org.example.presentation.dto.response.RequestLinkData

data class RequestLink(
    val id: String = ObjectId().toString(),
    val walletId: String,
    val blockchain: String,
    val address: String,
    val amount: Double,
    val shortCode: String,
    val createAt: String = TimeUtils.getCurrentUtcTimestamp().toString()
){
    fun toRequestLinkData() = RequestLinkData(
        id = id,
        address = address,
        blockchain = blockchain,
        amount = amount
    )
}
