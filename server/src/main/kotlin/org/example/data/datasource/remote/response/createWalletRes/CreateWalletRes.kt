package example.com.data.datasource.remote.response.createWalletRes

import kotlinx.serialization.Serializable
import org.example.data.datasource.remote.response.createWalletRes.Data

@Serializable
data class CreateWalletRes(
    val data: Data? = null,
)