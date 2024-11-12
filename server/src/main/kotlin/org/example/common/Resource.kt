package org.example.common

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed class Resource {
    @Serializable
    data class Success(
        val httpStatusCode: Int,
        val message: String,
        val data: JsonElement
    ) : Resource()

    @Serializable
    data class Failure(
        val httpStatusCode: Int,
        val error: String,
        val data: JsonElement? = null
    ) : Resource()
}
