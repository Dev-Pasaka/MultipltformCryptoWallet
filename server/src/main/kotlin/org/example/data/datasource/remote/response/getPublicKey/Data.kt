package example.com.data.datasource.remote.response.getPublicKey

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val publicKey: String
)