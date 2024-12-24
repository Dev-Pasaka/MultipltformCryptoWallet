package org.example.presentation.controller

import cryptoPaymentRequest
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.util.reflect.typeInfo
import org.example.presentation.dto.request.CreateRequestLinkReq
import org.example.presentation.dto.response.CreateRequestLinkRes
import org.example.presentation.dto.response.GetRequestLinkRes
import org.example.presentation.dto.response.RequestLinkData
import org.example.presentation.dto.response.cryptoPaymentError
import org.example.presentation.service.RequestLinkService

fun Route.requestLinkController(
    requestLinkService: RequestLinkService
){
    route("/request-link") {
        post {
            val body = call.receive<CreateRequestLinkReq>()
            val createRequestLink = requestLinkService.createRequestLink(body)
            call.respond(
                typeInfo = typeInfo<CreateRequestLinkRes>(),
                message = createRequestLink
            )
        }
        get {
            val shortCode = call.request.queryParameters["shortCode"] ?: return@get call.cryptoPaymentError(
                errorMessage = "Short code is required"
            )
            val getRequestLink = requestLinkService.getRequestLink(shortCode)
            call.cryptoPaymentRequest(
                id = getRequestLink.data?.id ?: "",
                address = getRequestLink.data?.address ?: "",
                blockchain =  getRequestLink.data?.blockchain ?: "",
                amount = getRequestLink.data?.amount ?: 0.0,
                qrData = getRequestLink.data ?: RequestLinkData(
                    id = "",
                    address = "",
                    blockchain = "",
                    amount = 0.0
                )
            )

        }
    }
}