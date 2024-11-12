package org.example.domain.repository

import example.com.data.datasource.remote.requests.TransferCryptoReq
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
import org.example.data.datasource.remote.response.createWalletSets.CreateWalletSetsRes
import org.example.data.datasource.remote.response.getWalletSet.GetWalletSetRes

interface CircleRepository {
    suspend fun getPublicKey(): GetPublicKeyRes
    suspend fun createWalletSets(idempotencyKey:String, name:String): CreateWalletSetsRes
    suspend fun getAllWalletSets(): GetAllWalletSetsRes
    suspend fun getWalletSet(id:String): GetWalletSetRes
    suspend fun updateWalletSet(id:String, name:String): UpdateWalletSetRes
    suspend fun createWallet(walletSetId:String, idempotencyKey:String): CreateWalletRes
    suspend fun getAllWallets(): GetWalletsRes
    suspend fun getWalletsByBlockchain(blockchain:String): GetWalletsRes
    suspend fun getWalletsByWalletId(walletId: String): GetWalletsRes
    suspend fun getWallet(id: String):GetWalletRes
    suspend fun getWalletBalance(walletId:String): GetWalletBalanceRes
    suspend fun transferCrypto(body: TransferCryptoReq): TransferCryptoRes
    suspend fun getAllTransactions():GetAllTransactionsRes
    suspend fun getTransaction(id:String): GetTransactionRes
    suspend fun validateAddress(walletAddress:String, blockchain: String):ValidateAddressRes
    suspend fun estimateGasFee(amount:String, destinationAddress:String, sourceAddress:String, blockchain: String):EstimateGasFeeRes
    suspend fun cancelTransaction(idempotencyKey:String, id:String):CancelTransactionRes
}