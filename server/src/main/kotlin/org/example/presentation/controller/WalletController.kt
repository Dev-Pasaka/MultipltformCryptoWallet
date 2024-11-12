package org.example.presentation.controller

import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.util.reflect.typeInfo
import org.example.presentation.dto.request.CreateWalletReq
import org.example.presentation.dto.request.RecoverWalletReq
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
        post("/restore") {
            val req = call.receive<RecoverWalletReq>()
            val res = walletService.restoreWallet(req.recoveryCode)
            call.respond(
                typeInfo = typeInfo<Any>(),
                message = res
            )
        }
    }


}
