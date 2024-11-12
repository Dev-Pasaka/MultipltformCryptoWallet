package example.com.data.datasource.remote.response.getTransaction

import kotlinx.serialization.Serializable

@Serializable
data class GetTransactionRes(
    val data: Data
)