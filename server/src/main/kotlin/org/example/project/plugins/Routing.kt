package org.example.project.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.presentation.controller.walletController
import org.example.presentation.service.WalletService
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val walletService by inject<WalletService>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        walletController(walletService)
    }



}
