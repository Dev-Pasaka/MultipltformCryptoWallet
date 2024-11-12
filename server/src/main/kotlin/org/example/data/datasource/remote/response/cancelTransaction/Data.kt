package example.com.data.datasource.remote.response.cancelTransaction

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val id: String,
    val state: String
)