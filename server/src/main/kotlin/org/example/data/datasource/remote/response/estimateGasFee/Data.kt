package example.com.data.datasource.remote.response.estimateGasFee

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val callGasLimit: String? = null,
    val high: High,
    val low: Low,
    val medium: Medium,
    val preVerificationGas: String? = null,
    val verificationGasLimit: String? = null
)