package org.example.di

import example.com.utils.*
import org.example.common.utils.MongoDBConfig
import org.example.common.utils.ServerConfig
import org.koin.dsl.module

val configModule = module {
    single { KtorClient }
    single { org.example.common.utils.TimeUtils }
    single { MongoDBConfig }
    single { JWTConfig }
    single { CircleConfig }
    single { ServerConfig }
}