package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class ValidateAddressReq(
    val address: String,
    val blockchain: String
)