package org.example.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object KtorClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = false
                ignoreUnknownKeys = true // Ignore unknown keys in JSON
            })
        }


        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
    //var baseUrl = "http://52.57.41.193"
    //var baseUrl = "http://192.168.0.105:8081"
    var baseUrl = "http://0.0.0.0:8081"
}