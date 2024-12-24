package org.example.data.remote.dto.response.createRequestLink

import kotlinx.serialization.Serializable
import org.example.common.Constants
import org.example.data.remote.KtorClient

@Serializable
data class CreateRequestLinRes(
    val httpStatusCode: Int,
    val message: String,
    val shortCode: String,
    val status: Boolean
){
    fun toLink(): String {
        val baseUrl = KtorClient.baseUrl
        val path = "/request-link"
        val params = "?shortCode=$shortCode"
        return "$baseUrl$path$params"
    }
}