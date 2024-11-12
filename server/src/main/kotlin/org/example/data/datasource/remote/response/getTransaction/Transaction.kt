package example.com.data.datasource.remote.response.getTransaction

import example.com.data.datasource.remote.response.getTransactions.EstimatedFee
import example.com.data.datasource.remote.response.getTransactions.FeeLevel
import example.com.data.datasource.remote.response.getTransactions.TransactionScreeningEvaluation
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val abiFunctionSignature: String? = null,
    val abiParameters: List<String>? = null,
    val amountInUSD: String? = null,
    val amounts: List<String>? = null,
    val blockHash: String,
    val blockHeight: Int,
    val blockchain: String,
    val contractAddress: String? = null,
    val createDate: String,
    val custodyType: String,
    val destinationAddress: String,
    val errorDetails: String? = null,
    val errorReason: String? = null,
    val estimatedFee: EstimatedFee? = null,
    val feeLevel: FeeLevel? = null,
    val firstConfirmDate: String,
    val id: String,
    val networkFee: String,
    val networkFeeInUSD: String? = null,
    val nfts: List<String>? = null,
    val operation: String,
    val refId: String? = null,
    val sourceAddress: String,
    val state: String,
    val tokenId: String,
    val transactionScreeningEvaluation: TransactionScreeningEvaluation? = null,
    val transactionType: String,
    val txHash: String,
    val updateDate: String,
    val userId: String? = null,
    val walletId: String
)