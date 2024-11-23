package org.example.domain.repository

import org.example.domain.entries.User
import org.example.presentation.dto.request.AddAccountToWalletReq
import org.example.presentation.dto.response.AddAccountToWalletRes
import org.example.presentation.dto.response.SignInRes
import org.example.presentation.dto.response.CreateUseRes
import org.example.presentation.dto.response.RegisterUserRes

interface UserRepository {
    suspend fun createUser(user:User): CreateUseRes
    suspend fun register(user: User): RegisterUserRes
    suspend fun signIn(email: String, password: String): SignInRes
    suspend fun addAccountToWallet(user: AddAccountToWalletReq, walletId: String): AddAccountToWalletRes
}