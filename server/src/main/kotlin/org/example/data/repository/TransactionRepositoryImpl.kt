package org.example.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.example.common.utils.MongoDBConfig
import org.example.common.utils.ServerConfig
import org.example.domain.repository.CircleRepository
import org.example.domain.repository.TransactionRepository
import org.example.domain.repository.WalletRepository
import org.example.domain.entries.Transaction
import org.example.domain.entries.TransactionStatus
import org.example.domain.entries.User
import org.example.domain.entries.Wallet
import org.example.presentation.dto.request.TransferCryptoReq
import org.example.presentation.dto.response.PersonChat
import org.example.presentation.dto.response.TransactionChat
import org.example.presentation.dto.response.TransferCryptoRes

class TransactionRepositoryImpl(
    private val db: MongoDBConfig,
    private val circleRepository: CircleRepository,
    private val walletRepository: WalletRepository
) : TransactionRepository {

    private val transactionCollection = db.database.getCollection<Transaction>("transaction")
    private val walletCollection = db.database.getCollection<Wallet>("wallet")
    private val userCollection = db.database.getCollection<User>("user")


    override suspend fun createTransaction(body: TransferCryptoReq): Pair<TransferCryptoRes, Transaction?> =
        withContext(Dispatchers.IO) {

            /**
             * Verify idempotency
             * */
            val doesTransactionExist = transactionCollection.find(
                Filters.eq(
                    Transaction::idempotencyKey.name,
                    body.idempotencyKey
                )
            ).firstOrNull()
            if (doesTransactionExist != null) {
                return@withContext Pair(
                    TransferCryptoRes(
                        httpStatusCode = 400,
                        status = false,
                        message = "Transaction in progress",
                    ), null
                )
            }

            /**
             * Check if wallet exists
             * */

            val wallet =
                walletCollection.find(Filters.eq(Wallet::id.name, body.walletId)).firstOrNull()
                    ?: return@withContext Pair(
                        TransferCryptoRes(
                            httpStatusCode = 400,
                            status = false,
                            message = "Wallet not found",
                        ), null
                    )



            /**
             * Check if wallet balance exists
             * */

            val getWalletBalance = try {
                circleRepository.getWalletBalance(wallet.walletId)
            } catch (e: Exception) {
                null
            } ?: return@withContext Pair(
                TransferCryptoRes(
                    httpStatusCode = 400,
                    status = false,
                    message = "Failed to get wallet balance",
                ), null
            )
            ServerConfig.logger.info("Wallet balance: ${getWalletBalance}")

            /**
             * Check if wallet balance is sufficient
             * */
            val walletBalance =
                getWalletBalance.data.tokenBalances.find { it.token.id == body.tokenId }
                    ?: return@withContext Pair(
                        TransferCryptoRes(
                            httpStatusCode = 400,
                            status = false,
                            message = "Wallet balance not found",
                        ), null

                    )

            ServerConfig.logger.info("Wallet balance: ${walletBalance}")

            /**
             * Estimate gas fees
             * */
            val gasFee = circleRepository.estimateGasFee(
                amount = body.amount.toString(),
                destinationAddress = body.destinationAddress,
                sourceAddress = wallet.address,
                blockchain = wallet.blockchain
            )

            /**
             * Check if gas fees plus amount is sufficient
             * */
            val totalAmount =
                body.amount + (gasFee.data?.high?.networkFee?.toDoubleOrNull() ?: 0.0)

            if ((walletBalance.amount.toDoubleOrNull() ?: 0.0) <= totalAmount) {
                return@withContext Pair(
                    TransferCryptoRes(
                        httpStatusCode = 400,
                        status = false,
                        message = "Insufficient balance",
                    ), null
                )
            }

            /**
             * Create transaction
             * */
            val transaction = Transaction(
                senderId = "",
                walletId = wallet.walletId,
                tokenId = walletBalance.token.id,
                amounts = listOf(body.amount.toString()),
                senderAddress = wallet.address,
                destinationAddress = body.destinationAddress,
                idempotencyKey = body.idempotencyKey,
                blockchain = wallet.blockchain,
                status = TransactionStatus.PENDING,
            )
            val createTransaction = transactionCollection.insertOne(
                transaction
            ).wasAcknowledged()

            return@withContext if (createTransaction) Pair(
                TransferCryptoRes(
                    httpStatusCode = 200,
                    status = true,
                    message = "Transaction created successfully",
                ), transaction
            ) else Pair(
                TransferCryptoRes(
                    httpStatusCode = 400,
                    status = false,
                    message = "Failed to create transaction",
                ), null
            )

        }


    override suspend fun updateTransactionStatus(
        id: String, status: TransactionStatus
    ): Boolean = withContext(Dispatchers.IO) {
        val updateTransaction = transactionCollection.updateOne(
            Filters.eq(Transaction::id.name, id), Updates.set(Transaction::status.name, status)
        ).wasAcknowledged()
        return@withContext updateTransaction

    }

    override suspend fun getTransactionsAsChats(walletId: String): List<Transaction> = withContext(
        Dispatchers.IO
    ) {
        val wallet = walletCollection.find(Filters.eq(Wallet::id.name, walletId)).firstOrNull()
            ?: return@withContext emptyList()
        val senderTransactions =
            transactionCollection.find(Filters.eq(Transaction::senderAddress.name, wallet.address))
                .toList()
        val receiverTransactions =
            transactionCollection.find(Filters.eq(Transaction::destinationAddress.name, wallet.address))
                .toList()
        return@withContext emptyList()

        TODO("Not yet implemented")
    }

    override suspend fun getGroupedTransactions(walletId: String): List<PersonChat> = withContext(Dispatchers.IO) {
        // Fetch the wallet using the wallet ID
        val wallet = walletCollection.find(Filters.eq(Wallet::id.name, walletId)).firstOrNull()
            ?: return@withContext emptyList()

        // Get transactions where the wallet is either the sender or the receiver
        val senderTransactions = transactionCollection.find(
            Filters.eq(Transaction::senderAddress.name, wallet.address)
        ).toList()

        val receiverTransactions = transactionCollection.find(
            Filters.eq(Transaction::destinationAddress.name, wallet.address)
        ).toList()

        // Combine both sender and receiver transactions
        val allTransactions = senderTransactions + receiverTransactions

        // Group transactions by the counterparty address
        val groupedChats = allTransactions.groupBy { tx ->
            if (wallet.address == tx.senderAddress) tx.destinationAddress else tx.senderAddress
        }

        // Map the grouped transactions into PersonChat format
        groupedChats.map { (personAddress, transactions) ->
            // Fetch the user associated with the wallet address (personAddress)
            val userWalletId = walletCollection.find(Filters.eq(Wallet::address.name, personAddress)).firstOrNull()
            ServerConfig.logger.info("UserWalletId: ${userWalletId}")
            val user = userCollection.find(Filters.eq(User::walletId.name, userWalletId?.id)).firstOrNull()
            println("User:$user")
            val personId = user?.username ?: personAddress // Use username if available, otherwise address

            PersonChat(
                personId = personId,
                personAddress = personAddress,
                transactions = transactions.map { tx ->
                    TransactionChat(
                        id = tx.id,
                        senderId = tx.senderId,
                        senderAddress = tx.senderAddress,
                        receiverAddress = tx.destinationAddress,
                        amount = tx.amounts.firstOrNull() ?: "0.0",
                        feeLevel = tx.feeLevel,
                        blockchain = tx.blockchain,
                        status = tx.status.name,
                        timestamp = tx.timestamp,
                        message = "Sent ${tx.amounts.firstOrNull() ?: "0.0"} " +
                                "${tx.blockchain} from ${tx.senderAddress} to ${tx.destinationAddress}"
                    )
                }
            )
        }
    }
}