package org.example.data.remote.dto.response.getRequestLink

import kotlinx.serialization.Serializable
import org.example.domain.model.RequestLinkData

@Serializable
data class GetRequestLinkDataRes(
    val `data`: Data,
    val httpStatusCode: Int,
    val message: String,
    val status: Boolean
){
    fun toRequestLinkData() = RequestLinkData(
        id = data.id,
        address = data.address,
        blockchain = data.blockchain,
        amount = data.amount
    )
}