package org.example.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.data.remote.KtorClient
import org.example.data.remote.dto.request.CreateRequestLinkReq
import org.example.data.remote.dto.response.createRequestLink.CreateRequestLinRes
import org.example.data.remote.dto.response.getRequestLink.GetRequestLinkDataRes
import org.example.domain.repository.RequestLinkRepository

class RequestLinkRepositoryImpl(
    private val api: KtorClient
): RequestLinkRepository {
    override suspend fun createRequestLink(body: CreateRequestLinkReq): CreateRequestLinRes = withContext(Dispatchers.IO) {
        api.client.post("${api.baseUrl}/request-link"){
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    override suspend fun getRequestLink(shortCode: String): GetRequestLinkDataRes = withContext(Dispatchers.IO) {
        api.client.get("${api.baseUrl}/request-link?shortCode=$shortCode").body()
    }
}