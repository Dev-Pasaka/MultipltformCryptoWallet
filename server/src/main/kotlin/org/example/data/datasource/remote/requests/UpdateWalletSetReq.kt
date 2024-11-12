package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateWalletSetReq(
    val name:String,
)
