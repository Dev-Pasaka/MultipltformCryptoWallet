package org.example.project

import org.example.project.plugins.prometheus
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import org.example.project.plugins.configureAdministration
import org.example.project.plugins.configureKoin
import org.example.project.plugins.configureKotlinxRpc
import org.example.project.plugins.configureMonitoring
import org.example.project.plugins.configureRouting
import org.example.project.plugins.configureSecurity
import org.example.project.plugins.configureSerialization
import org.example.project.plugins.configureWebsocket
import org.example.project.plugins.corsConfiguration
import kotlin.time.Duration.Companion.seconds

fun main() {
    embeddedServer(
        Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(WebSockets){
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    configureKoin()
    configureSecurity()
    //configureWebsocket()
    configureRouting()
    configureKotlinxRpc()
    configureMonitoring()
    configureSerialization()
    configureAdministration()
    prometheus()
    corsConfiguration()
}