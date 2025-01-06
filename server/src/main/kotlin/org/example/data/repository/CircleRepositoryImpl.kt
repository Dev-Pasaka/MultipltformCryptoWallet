package org.example.data.repository

import example.com.data.datasource.remote.requests.*
import example.com.data.datasource.remote.response.cancelTransaction.CancelTransactionRes
import example.com.data.datasource.remote.response.createWalletRes.CreateWalletRes
import example.com.data.datasource.remote.response.estimateGasFee.EstimateGasFeeRes
import example.com.data.datasource.remote.response.getAllWalletSets.GetAllWalletSetsRes
import example.com.data.datasource.remote.response.getPublicKey.GetPublicKeyRes
import example.com.data.datasource.remote.response.getTransaction.GetTransactionRes
import example.com.data.datasource.remote.response.getTransactions.GetAllTransactionsRes
import example.com.data.datasource.remote.response.getWallet.GetWalletRes
import example.com.data.datasource.remote.response.getWalletBalance.GetWalletBalanceRes
import example.com.data.datasource.remote.response.getWallets.GetWalletsRes
import example.com.data.datasource.remote.response.transferCrypto.TransferCryptoRes
import example.com.data.datasource.remote.response.updateWalletSet.UpdateWalletSetRes
import example.com.data.datasource.remote.response.validateAddress.ValidateAddressRes
import org.example.domain.repository.EncryptionRepository
import example.com.utils.CircleConfig
import example.com.utils.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.example.data.datasource.remote.requests.CancelTransactionReq
import org.example.data.datasource.remote.response.createWalletSets.CreateWalletSetsRes
import org.example.data.datasource.remote.response.getWalletSet.GetWalletSetRes
import org.example.domain.repository.CircleRepository
import java.util.*

class CircleRepositoryImpl(
    private val api: KtorClient,
    private val circle: CircleConfig,
    private val encryptionRepository: EncryptionRepository,
) : CircleRepository {

    /** Authentication */
    override suspend fun getPublicKey(): GetPublicKeyRes = withContext(Dispatchers.IO) {
        val publicKey = api.client.get("${circle.baseUrl}${circle.publicKey}") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetPublicKeyRes>()
        publicKey

    }

    /** Wallets */
    override suspend fun createWalletSets(idempotencyKey: String, name: String): CreateWalletSetsRes =
        withContext(Dispatchers.IO) {
            val publicKey = getPublicKey()
            val entitySecretCiphertext = encryptionRepository.encryptEntitySecrete(
                publicKey = publicKey.data?.publicKey ?: "",
                entitySecrete = circle.entitySecret
            )
            api.client.post(urlString = "${circle.baseUrl}${circle.createWalletSets}") {
                header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
                contentType(ContentType.Application.Json)
                setBody(
                    CreateWalletSetsReq(
                        entitySecretCipherText = entitySecretCiphertext,
                        idempotencyKey = idempotencyKey,
                        name = name
                    )
                )
            }.body<CreateWalletSetsRes>()

        }

    override suspend fun getAllWalletSets(): GetAllWalletSetsRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}walletSets") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetAllWalletSetsRes>()
    }

    override suspend fun getWalletSet(id: String): GetWalletSetRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}walletSets/$id") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetWalletSetRes>()
    }

    override suspend fun updateWalletSet(id: String, name: String): UpdateWalletSetRes = withContext(Dispatchers.IO) {
        api.client.put("${circle.baseUrl}developer/walletSets/$id") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
            contentType(ContentType.Application.Json)
            setBody(UpdateWalletSetReq(name = name))
        }.body<UpdateWalletSetRes>()

    }

    override suspend fun createWallet(walletSetId: String, idempotencyKey: String): CreateWalletRes =
        withContext(Dispatchers.IO) {
            val publicKey = getPublicKey()
            val entitySecretCiphertext = encryptionRepository.encryptEntitySecrete(
                publicKey = publicKey.data?.publicKey ?: "",
                entitySecrete = circle.entitySecret
            )
            api.client.post(urlString = "${circle.baseUrl}${circle.createWallet}") {
                header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
                contentType(ContentType.Application.Json)
                setBody(
                    CreateWalletReq(
                        entitySecretCipherText = entitySecretCiphertext,
                        idempotencyKey = idempotencyKey,
                        walletSetId = walletSetId,
                        blockchains = listOf("ETH-SEPOLIA", "MATIC-AMOY", "SOL-DEVNET")
                    )
                )
            }.body<CreateWalletRes>()
        }

    override suspend fun getAllWallets(): GetWalletsRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}wallets") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetWalletsRes>()
    }

    override suspend fun getWalletsByBlockchain(blockchain: String): GetWalletsRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}wallets") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
            parameter("blockchain", blockchain)
        }.body<GetWalletsRes>()
    }

    override suspend fun getWalletsByWalletId(walletId: String): GetWalletsRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}wallets") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
            parameter("walletId", walletId)
        }.body<GetWalletsRes>()
    }

    override suspend fun getWallet(id: String): GetWalletRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}wallets/$id") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetWalletRes>()
    }

    override suspend fun getWalletBalance(walletId: String): GetWalletBalanceRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}wallets/$walletId/balances") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetWalletBalanceRes>()
    }

    /** Transactions */
    override suspend fun transferCrypto(body: TransferCryptoReq): TransferCryptoRes = withContext(Dispatchers.IO) {
        val publicKey = getPublicKey()
        val entitySecretCiphertext = encryptionRepository.encryptEntitySecrete(
            publicKey = publicKey.data?.publicKey ?: "",
            entitySecrete = circle.entitySecret
        )
        try {
            val result = api.client.post(urlString = "${circle.baseUrl}developer/transactions/transfer") {
                header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
                contentType(ContentType.Application.Json)
                setBody(
                    body.copy(
                        entitySecretCipherText = entitySecretCiphertext,
                        tokenId = body.tokenId
                    )
                )
            }.body<TransferCryptoRes>()
            println(result)
            result


        }catch (e:Exception){
            e.printStackTrace()
            TransferCryptoRes()
        }

    }

    override suspend fun getAllTransactions(): GetAllTransactionsRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}transactions") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetAllTransactionsRes>()
    }

    override suspend fun getAllTransactionsInWallet(walletIds: List<String>):GetAllTransactionsRes = withContext(Dispatchers.IO) {
        val formattedStr = walletIds.joinToString(",") { "[$it]" }
        println(formattedStr)
        api.client.get(urlString = "${circle.baseUrl}transactions") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
            parameter("walletIds", formattedStr)
        }.body<GetAllTransactionsRes>()
    }

    override suspend fun getTransaction(id: String): GetTransactionRes = withContext(Dispatchers.IO) {
        api.client.get(urlString = "${circle.baseUrl}transactions/$id") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
        }.body<GetTransactionRes>()
    }

    override suspend fun validateAddress(walletAddress: String, blockchain: String): ValidateAddressRes = withContext(Dispatchers.IO) {
        val result = api.client.post(urlString = "${circle.baseUrl}transactions/validateAddress") {
            header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
            contentType(ContentType.Application.Json)
            setBody(
                ValidateAddressReq(
                    address =walletAddress,
                    blockchain = blockchain
                )
            )
        }.bodyAsText()
        println(result)
        ValidateAddressRes()
    }

    override suspend fun estimateGasFee(amount: String, destinationAddress: String, sourceAddress:String, blockchain: String): EstimateGasFeeRes =
        withContext(Dispatchers.IO) {
            try {
                api.client.post(urlString = "${circle.baseUrl}transactions/transfer/estimateFee") {
                    header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
                    contentType(ContentType.Application.Json)
                    setBody(
                        EstimateGasFeeReq(
                            destinationAddress =destinationAddress,
                            sourceAddress = sourceAddress,
                            amounts = listOf(amount),
                            blockchain = blockchain
                        )
                    )
                }.body<EstimateGasFeeRes>()
            }catch (e:Exception){
                e.printStackTrace()
                EstimateGasFeeRes()
            }
        }

    override suspend fun cancelTransaction(idempotencyKey: String, id: String): CancelTransactionRes = withContext(Dispatchers.IO) {
        try {
            val publicKey = getPublicKey()
            val entitySecretCiphertext = encryptionRepository.encryptEntitySecrete(
                publicKey = publicKey.data?.publicKey ?: "",
                entitySecrete = circle.entitySecret
            )
            api.client.post(urlString = "${circle.baseUrl}developer/transactions/$id/cancel") {
                header(HttpHeaders.Authorization, "Bearer ${circle.apiKey}")
                contentType(ContentType.Application.Json)
                setBody(
                    CancelTransactionReq(
                        idempotencyKey = idempotencyKey,
                        entitySecretCiphertext = entitySecretCiphertext
                    )
                )
            }.body<CancelTransactionRes>()
        }catch (e:Exception){
            e.printStackTrace()
            CancelTransactionRes()
        }
    }
}

