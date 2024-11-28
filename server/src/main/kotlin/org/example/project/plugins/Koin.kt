package org.example.project.plugins


import org.example.di.configModule
import io.ktor.server.application.*
import org.example.di.channelModule
import org.example.di.repositoryModule
import org.example.di.serviceModule
import org.example.di.useCaseModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            listOf(
                useCaseModule,
                repositoryModule,
                serviceModule,
                configModule,
                channelModule
            )
        )
    }
}
