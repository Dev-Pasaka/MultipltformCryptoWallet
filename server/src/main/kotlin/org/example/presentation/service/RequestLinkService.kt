package org.example.presentation.service

import org.example.domain.usecases.requestLink.CreateRequestLinkUseCase
import org.example.domain.usecases.requestLink.GetRequestLinkUseCase
import org.example.presentation.dto.request.CreateRequestLinkReq
import org.example.presentation.dto.response.CreateRequestLinkRes
import org.example.presentation.dto.response.GetRequestLinkRes

class RequestLinkService(
    private val createRequestLinkUseCase: CreateRequestLinkUseCase,
    private val getRequestLinkUseCase: GetRequestLinkUseCase
) {
    suspend fun createRequestLink(createRequestLinkReq: CreateRequestLinkReq): CreateRequestLinkRes {
        return createRequestLinkUseCase(createRequestLinkReq)
    }
    suspend fun getRequestLink(shortCode: String): GetRequestLinkRes {
        return getRequestLinkUseCase(shortCode)
    }

}