package example.com.data.datasource.remote.response.cancelTransaction

import kotlinx.serialization.Serializable

@Serializable
data class CancelTransactionRes(
    val data: Data? = null
)