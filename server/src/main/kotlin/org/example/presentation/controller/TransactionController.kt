package org.example.presentation.controller

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.util.reflect.typeInfo
import org.example.presentation.dto.request.TransferCryptoReq
import org.example.presentation.dto.response.TransactionRes
import org.example.presentation.dto.response.TransferCryptoRes
import org.example.presentation.service.TransactionService

fun Route.transactionController(transactionService: TransactionService) {
    route("/transaction") {
        post {
            val body = call.receive<TransferCryptoReq>()
            val response = transactionService.createTransaction(body)
            call.respond(
                typeInfo = typeInfo<TransferCryptoRes>(),
                message = response
            )
        }
        get("/history"){
            val walletId = call.request.queryParameters["walletId"] ?: return@get call.respond(
                typeInfo = typeInfo<TransferCryptoRes>(),
                message = "walletId is required"
            )
            val response = transactionService.transactionHistory(walletId)
            call.respond(
                typeInfo = typeInfo<TransactionRes>(),
                message = response
            )
        }
    }
}