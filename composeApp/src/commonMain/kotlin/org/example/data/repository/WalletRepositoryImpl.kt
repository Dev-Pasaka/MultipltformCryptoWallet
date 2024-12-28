package org.example.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.data.remote.KtorClient
import org.example.data.remote.dto.request.CreateWalletReq
import org.example.data.remote.dto.request.ImportWalletReq
import org.example.data.remote.dto.request.TransferCryptoReq
import org.example.data.remote.dto.response.createWallet.CreateWalletRes
import org.example.data.remote.dto.response.getTransactions.GetTransactionsRes
import org.example.data.remote.dto.response.getWallet.GetWalletRes
import org.example.data.remote.dto.response.getWalletBalance.GetWalletBalanceRes
import org.example.data.remote.dto.response.importWallet.ImportWalletRes
import org.example.data.remote.dto.response.transferCrypto.TransferCryptoRes
import org.example.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val api: KtorClient
) : WalletRepository {
    override suspend fun createWallet(body: CreateWalletReq): CreateWalletRes =
        withContext(Dispatchers.IO) {
            api.client.post("${api.baseUrl}/wallet") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
        }

    override suspend fun importWallet(body: ImportWalletReq): ImportWalletRes {
        return withContext(Dispatchers.IO) {
            api.client.post("${api.baseUrl}/wallet/restore") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body()
        }
    }

    override suspend fun getWallet(id: String): GetWalletRes = withContext(Dispatchers.IO) {
        val result = api.client.get("${api.baseUrl}/wallet?id=$id").body<GetWalletRes>()
        println("result: $result")
        return@withContext result
    }

    override suspend fun getWalletBalance(walletId: String): GetWalletBalanceRes =
        withContext(Dispatchers.IO) {
            val result = api.client.get("${api.baseUrl}/wallet/balance?walletId=$walletId")
                .body<GetWalletBalanceRes>()
            println("result: $result")
            return@withContext result

        }

    override suspend fun transferCrypto(body: TransferCryptoReq): TransferCryptoRes = withContext(
        Dispatchers.IO
    ) {
        return@withContext api.client.post("${api.baseUrl}/transaction") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    override suspend fun getTransactions(walletId: String): GetTransactionsRes = withContext(
        Dispatchers.IO
    ) {
        return@withContext api.client.get("${api.baseUrl}/transaction/history?walletId=$walletId")
            .body()

    }
}