package example.com.data.datasource.remote.response.transferCrypto

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val id: String,
    val state: String
)