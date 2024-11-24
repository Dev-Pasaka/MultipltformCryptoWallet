package org.example.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bson.Document
import org.example.common.utils.MongoDBConfig
import org.example.domain.entries.User
import org.example.domain.entries.Wallet
import org.example.domain.entries.WalletSet
import org.example.domain.repository.CircleRepository
import org.example.domain.repository.EncryptionRepository
import org.example.domain.repository.UserRepository
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.response.CreateWalletRes
import org.example.presentation.dto.response.GetWalletRes
import org.example.presentation.dto.response.RestoreWalletRes
import org.example.presentation.dto.response.RestoreWalletSecrete
import org.example.presentation.dto.response.SecretsRes
import org.example.presentation.dto.response.WalletSearchRes
import java.util.UUID

class WalletRepositoryImpl(
    private val db: MongoDBConfig,
    private val circleRepository: CircleRepository,
    private val encryptionRepository: EncryptionRepository,
    private val userRepository: Lazy<UserRepository>
) : WalletRepository {

    private val walletSetCollection = db.database.getCollection<WalletSet>("wallet_set")
    private val walletCollection = db.database.getCollection<Wallet>("wallet")
    private val userCollection = db.database.getCollection<User>("user")


    override suspend fun createWalletSet(): Pair<Boolean, String> {

        val doesWalletSetExist = walletSetCollection.find().toList().firstOrNull()
        if (doesWalletSetExist != null) return Pair(false, "Wallet set already exists")

        val idempotencyKey = UUID.randomUUID().toString()
        val createWalletSetRes = circleRepository.createWalletSets(
            idempotencyKey = idempotencyKey,
            name = "kotlin-conf-25-test-wallet-set"
        )

        if (createWalletSetRes.data == null) return Pair(false, "Failed to create wallet set")
        val walletSet = walletSetCollection.insertOne(
            WalletSet(
                id = createWalletSetRes.data.walletSet.id,
                name = createWalletSetRes.data.walletSet.id,
                custodyType = createWalletSetRes.data.walletSet.custodyType,
                createDate = createWalletSetRes.data.walletSet.createDate,
                updateDate = createWalletSetRes.data.walletSet.updateDate,
                idempotencyKey = idempotencyKey,
            )
        )

        if (walletSet.insertedId == null) return Pair(false, "Failed to create wallet set")
        return Pair(true, "Wallet set created successfully")

    }

    override suspend fun createWallet(idempotencyKey: String): CreateWalletRes =
        withContext(Dispatchers.IO) {

            val doesWalletSetExist = walletSetCollection.find().toList().firstOrNull()
                ?: return@withContext CreateWalletRes(
                    httpStatusCode = HttpStatusCode.BadRequest.value,
                    status = false,
                    message = "Wallet set does not exist",
                    wallet = null
                )
            val doesWalletExist =
                walletCollection.find(Filters.eq(Wallet::idempotencyKey.name, idempotencyKey))
                    .toList()

            if (doesWalletExist.isNotEmpty()) {

                return@withContext CreateWalletRes(
                    httpStatusCode = HttpStatusCode.OK.value,
                    status = true,
                    message = "Wallet already exists",
                    wallet = doesWalletExist.map { it.toGetWalletRes() },
                )
            }
            val createWallet = circleRepository.createWallet(
                walletSetId = doesWalletSetExist.id,
                idempotencyKey = idempotencyKey
            )

            val secretPhrase = encryptionRepository.generate32BitSecretPhrase()
            val generalWalletId = UUID.randomUUID().toString()

            if (createWallet.data == null) return@withContext CreateWalletRes(
                httpStatusCode = HttpStatusCode.BadRequest.value,
                status = false,
                message = "Failed to create wallet",
                wallet = null

            )

            createWallet.data.wallets.forEach { wallet ->
                walletCollection.insertOne(
                    Wallet(
                        id = generalWalletId,
                        state = wallet.state,
                        walletSetId = wallet.walletSetId,
                        custodyType = wallet.custodyType,
                        accountType = wallet.accountType,
                        address = wallet.address,
                        blockchain = wallet.blockchain,
                        updateDate = wallet.updateDate,
                        createDate = wallet.createDate,
                        walletId = wallet.id,
                        idempotencyKey = idempotencyKey,
                        recoveryCode = secretPhrase
                    )
                )
            }

            launch {
                userCollection.insertOne(
                    User(
                        walletId = generalWalletId,
                        idempotencyKey = idempotencyKey
                    )
                )
            }

            return@withContext CreateWalletRes(
                httpStatusCode = HttpStatusCode.OK.value,
                status = true,
                message = "Wallet created successfully",
                secrets = SecretsRes(
                    id = generalWalletId,
                    recoverCode = secretPhrase
                ),
                wallet = createWallet.data.toCreateWalletRes()
            )


        }

    override suspend fun getAllWallets(): Unit {
        TODO("Not yet implemented")
    }

    override suspend fun getWallet(id: String): GetWalletRes = withContext(Dispatchers.IO) {
        val wallet = walletCollection.find(
            Filters.eq(Wallet::id.name, id)
        ).toList()

        if (wallet.isEmpty()) return@withContext GetWalletRes(
            httpStatusCode = HttpStatusCode.BadRequest.value,
            status = false,
            message = "Wallet not found",
            data = null
        )


        GetWalletRes(
            httpStatusCode = HttpStatusCode.OK.value,
            status = true,
            message = "Wallet retrieved successfully",
            data = wallet.map { it.toGetWalletRes() }
        )
    }

    override suspend fun restoreWallet(recoverCode: String): RestoreWalletRes =
        withContext(Dispatchers.IO) {
            val existingWallets =
                walletCollection.find(Filters.eq(Wallet::recoveryCode.name, recoverCode)).toList()
            if (existingWallets.isEmpty()) {
                return@withContext RestoreWalletRes(
                    httpStatusCode = HttpStatusCode.BadRequest.value,
                    status = false,
                    message = "Invalid recovery code or wallet not found",
                )
            }

            val newWalletId = UUID.randomUUID().toString()

            val updateResult = walletCollection.updateMany(
                Filters.eq(Wallet::recoveryCode.name, recoverCode),
                listOf(
                    Updates.set(Wallet::id.name, newWalletId),
                    Updates.set(Wallet::recoveryCode.name, UUID.randomUUID().toString())
                )
            ).wasAcknowledged()

            if (!updateResult) {
                return@withContext RestoreWalletRes(
                    httpStatusCode = HttpStatusCode.InternalServerError.value,
                    status = false,
                    message = "Failed to restore wallet"
                )
            }

            val restoredWallets =
                walletCollection.find(Filters.eq(Wallet::id.name, newWalletId)).toList()

            return@withContext RestoreWalletRes(
                httpStatusCode = HttpStatusCode.OK.value,
                status = true,
                message = "Wallet restored successfully",
                secrets = RestoreWalletSecrete(
                    id = newWalletId,
                    recoverCode = recoverCode
                ),
                data = restoredWallets.map { it.toGetWalletRes() }
            )
        }

    override suspend fun searchWallet(searchText: String): Flow<WalletSearchRes> = flow {
        val searchQuery = listOf(
            Document(
                "\$search", Document()
                    .append("index", "user_search")
                    .append(
                        "text", Document()
                            .append("query", searchText)
                            .append("path", listOf("email", "phone", "username"))
                            .append(
                                "fuzzy", Document()
                                    .append("maxEdits", 2)
                                    .append("prefixLength", 2)
                            )
                    )
            )
        )

        try {
            userCollection.aggregate(searchQuery).collect { user ->
                println("MongoDB Search Result: $user")
                val wallet = walletCollection.find(Filters.eq(Wallet::id.name, user.walletId))
                val result = WalletSearchRes(
                    status = wallet.toList().isNotEmpty(),
                    message = "Search results",
                    username = user.username,
                    data = wallet.toList().map { it.toWalletSearch() }
                )
                emit(result)
            }
        } catch (e: Exception) {
            println("Error during aggregation: ${e.message}")
        }
    }


    init {
        CoroutineScope(Dispatchers.IO).launch { createWalletSet() }
    }

}