package example.com.data.datasource.remote.response.getWallets

import kotlinx.serialization.Serializable

@Serializable
data class GetWalletsRes(
    val data: Data? = null,
)