package example.com.data.datasource.remote.response.estimateGasFee

import kotlinx.serialization.Serializable

@Serializable
data class EstimateGasFeeRes(
    val data: Data? = null
)