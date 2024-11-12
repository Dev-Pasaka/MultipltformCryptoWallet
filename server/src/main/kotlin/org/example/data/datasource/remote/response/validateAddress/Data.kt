package example.com.data.datasource.remote.response.validateAddress

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val isValid: Boolean
)