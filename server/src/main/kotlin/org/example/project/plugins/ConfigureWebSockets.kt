package org.example.project.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Route
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlin.time.Duration.Companion.seconds

fun Application.configureWebsocket(){
    /*install(WebSockets){
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }*/

}