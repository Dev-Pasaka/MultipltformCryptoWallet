package org.example.project

import example.com.application.plugins.prometheus
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.plugins.configureAdministration
import org.example.project.plugins.configureKoin
import org.example.project.plugins.configureMonitoring
import org.example.project.plugins.configureRouting
import org.example.project.plugins.configureSecurity
import org.example.project.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureAdministration()
    configureRouting()
    prometheus()
}