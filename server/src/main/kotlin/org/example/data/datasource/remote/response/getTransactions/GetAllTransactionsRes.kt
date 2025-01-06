package example.com.data.datasource.remote.response.getTransactions

import kotlinx.serialization.Serializable
import org.example.presentation.dto.response.TransactionRes

@Serializable
data class GetAllTransactionsRes(
    val data: Data
){
    fun toTransactionRes() = TransactionRes(
        httpStatusCode = 200,
        status = true,
        message = "Transactions retrieved successfully",
        data = data.transactions.map { it.toTransaction() }
    )
}