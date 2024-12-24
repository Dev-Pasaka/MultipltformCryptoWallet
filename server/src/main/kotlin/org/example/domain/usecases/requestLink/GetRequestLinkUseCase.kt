package org.example.domain.usecases.requestLink

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.repository.RequestLinkRepository
import org.example.presentation.dto.response.GetRequestLinkRes

class GetRequestLinkUseCase(
    private val requestLinkRepository: RequestLinkRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(shortCode: String): GetRequestLinkRes{
        return try {
            requestLinkRepository.getRequestLink(shortCode)
        }catch (e: Exception){
            e.printStackTrace()
            serverConfig.logger.warn("Failed to get request link: ${e.message}")
            return GetRequestLinkRes(
                httpStatusCode = HttpStatusCode.InternalServerError.value,
                status = false,
                message = "Failed to get request link"
            )
        }
    }
}