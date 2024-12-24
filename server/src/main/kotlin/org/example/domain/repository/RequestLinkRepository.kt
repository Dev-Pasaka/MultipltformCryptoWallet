package org.example.domain.repository

import org.example.domain.entries.RequestLink
import org.example.presentation.dto.response.CreateRequestLinkRes
import org.example.presentation.dto.response.GetRequestLinkRes

interface RequestLinkRepository {
    suspend fun createRequestLink(requestLink: RequestLink): CreateRequestLinkRes
    suspend fun getRequestLink(shortCode: String): GetRequestLinkRes
}