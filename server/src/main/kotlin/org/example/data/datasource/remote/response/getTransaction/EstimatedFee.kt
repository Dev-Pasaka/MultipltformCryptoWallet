package example.com.data.datasource.remote.response.getTransaction

import kotlinx.serialization.Serializable

@Serializable
data class EstimatedFee(
    val baseFee: String,
    val gasLimit: String,
    val gasPrice: String,
    val maxFee: String,
    val networkFee: String,
    val priorityFee: String
)