package example.com.data.datasource.remote.response.getTransactions

import kotlinx.serialization.Serializable

@Serializable
data class GetAllTransactionsRes(
    val data: Data
)