package example.com.data.datasource.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class EstimateGasFeeReq(
    val destinationAddress:String,
    val sourceAddress:String,
    val blockchain:String,
    val amounts:List<String>
)
