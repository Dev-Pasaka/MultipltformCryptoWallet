package org.example.domain.usecases.requestLink

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.repository.RequestLinkRepository
import org.example.presentation.dto.request.CreateRequestLinkReq
import org.example.presentation.dto.response.CreateRequestLinkRes

class CreateRequestLinkUseCase(
    private val requestLinkRepository: RequestLinkRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(createRequestLinkReq: CreateRequestLinkReq): CreateRequestLinkRes {
        return try {
           requestLinkRepository.createRequestLink(createRequestLinkReq.toRequestLink())
        }catch (e: Exception){
            e.printStackTrace()
            serverConfig.logger.warn("Failed to create request link: ${e.message}")
            return CreateRequestLinkRes(
                httpStatusCode = HttpStatusCode.InternalServerError.value,
                status = false,
                shortCode = "",
                message = "Failed to create request link"
            )
        }
    }
}