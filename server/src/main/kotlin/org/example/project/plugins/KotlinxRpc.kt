package org.example.project.plugins

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.url
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.routing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.rpc.RPCClient
import kotlinx.rpc.RPCServer
import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.ktor.server.RPC
import kotlinx.rpc.krpc.rpcServerConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import kotlinx.serialization.json.Json
import javax.print.PrintServiceLookup
import javax.print.PrintServiceLookup.registerService
import kotlin.coroutines.CoroutineContext

fun Application.configureKotlinxRpc() {
    routing {
        rpc("/api") {
            rpcServerConfig {
                serialization {
                    json(Json {
                        ignoreUnknownKeys = true // Optional: Customize JSON configuration
                        prettyPrint = false
                    })
                }
            }
            registerService<MyService> { ctx -> MyServiceImpl(ctx) }
        }
    }
}

@Rpc
interface MyService : RemoteService {
    suspend fun hello(name: String): String
}

class MyServiceImpl(override val coroutineContext: CoroutineContext) : MyService {
    override suspend fun hello(name: String): String {
        return "Hello, $name! I'm server!"
    }
}



suspend fun createRpcClient(url: String): RPCClient {
    // Create an HttpClient with CIO engine for the RPC client
    val client = HttpClient(CIO){
        install(WebSockets)

    }
    return client.rpc(url) {
        rpcConfig {
            serialization {
                json()
            }
        }
    }
}

fun RPCClient.getMyService(): MyService {
    // Use the RPCClient to get a proxy to the MyService interface
    return withService()
}


fun main() = runBlocking {
    val rpcClient = createRpcClient("ws://localhost:8081/api") // Update URL to match your server
    val myService = rpcClient.getMyService()

    // Call the `hello` method from the MyService interface
    val response = myService.hello("World")
    println(response) // Should print: "Hello, World! I'm server!"
}