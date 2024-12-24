package org.example.domain.repository

import org.example.data.remote.dto.request.CreateRequestLinkReq
import org.example.data.remote.dto.response.createRequestLink.CreateRequestLinRes
import org.example.data.remote.dto.response.getRequestLink.GetRequestLinkDataRes

interface RequestLinkRepository {
    suspend fun createRequestLink(body: CreateRequestLinkReq): CreateRequestLinRes

    suspend fun getRequestLink(shortCode: String): GetRequestLinkDataRes
}