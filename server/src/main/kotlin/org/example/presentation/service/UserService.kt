package org.example.presentation.service

import org.example.domain.usecases.user.AddAccountToWalletUseCase
import org.example.domain.usecases.user.RegisterUserUseCase
import org.example.domain.usecases.user.SignInUseCase
import org.example.presentation.dto.request.AddAccountToWalletReq
import org.example.presentation.dto.request.RegisterUserReq

class UserService(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val addAccountToWalletUseCase: AddAccountToWalletUseCase
) {
    suspend fun registerUser(registerUserReq: RegisterUserReq) =
        registerUserUseCase.invoke(registerUserReq.toUser())

    suspend fun signIn(email: String, password: String) =
        signInUseCase.invoke(email, password)

    suspend fun addAccountToWallet(user: AddAccountToWalletReq, walletId: String) =
        addAccountToWalletUseCase.invoke(user, walletId)

}