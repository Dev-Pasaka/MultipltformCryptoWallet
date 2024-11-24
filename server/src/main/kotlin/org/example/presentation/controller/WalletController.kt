package org.example.presentation.controller

import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.websocket.webSocket
import io.ktor.util.reflect.typeInfo
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import org.example.presentation.dto.request.CreateWalletReq
import org.example.presentation.dto.request.RecoverWalletReq
import org.example.presentation.dto.response.WalletSearchRes
import org.example.presentation.service.WalletService

fun Route.walletController(
    walletService: WalletService
) {
    route("/wallet") {

        post {
            val req = call.receive<CreateWalletReq>()
            val res = walletService.createWallet(req.idempotencyKey)
            call.respond(
                typeInfo = typeInfo<Any>(),
                message = res
            )
        }

        get {
            val id = call.parameters["id"] ?: ""
            val res = walletService.getWallet(id)
            call.respond(
                typeInfo = typeInfo<Any>(),
                message = res
            )
        }

        authenticate {
            get("/auth") {
                val id = call.principal<JWTPrincipal>()?.payload?.getClaim("walletId").toString()
                    .removeSurrounding("\"")
                val res = walletService.getWallet(id)
                call.respond(
                    typeInfo = typeInfo<Any>(),
                    message = res
                )
            }
        }
        post("/restore") {
            val req = call.receive<RecoverWalletReq>()
            val res = walletService.restoreWallet(req.recoveryCode)
            call.respond(
                typeInfo = typeInfo<Any>(),
                message = res
            )
        }




    }


    webSocket("/search") {
        for (frame in incoming) {
            if (frame is Frame.Text) {
                val searchText = frame.readText()
                try {
                    walletService.searchWallet(searchText)
                        .onStart { send(Frame.Text("Search started...")) }
                        .onCompletion { send(Frame.Text("Search completed.")) }
                        .collect { result ->
                            send(Frame.Text(Json.encodeToString(WalletSearchRes.serializer(), result))) // Stream each result to the client
                        }
                } catch (e: Exception) {
                    send(Frame.Text("Error occurred: ${e.message}"))
                }
            }
        }
    }

}
