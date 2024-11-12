package example.com.data.datasource.remote.response.estimateGasFee

import kotlinx.serialization.Serializable

@Serializable
data class High(
    val baseFee: String,
    val gasLimit: String,
    val gasPrice: String? = null,
    val maxFee: String? = null,
    val networkFee: String,
    val priorityFee: String
)