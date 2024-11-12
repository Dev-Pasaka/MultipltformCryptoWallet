package example.com.data.datasource.remote.response.getTransactions

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val transactions: List<Transaction>
)