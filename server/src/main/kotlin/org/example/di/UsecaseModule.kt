package org.example.di


import org.example.domain.usecases.user.AddAccountToWalletUseCase
import org.example.domain.usecases.user.RegisterUserUseCase
import org.example.domain.usecases.user.SignInUseCase
import org.example.domain.usecases.wallet.CreateWalletUseCase
import org.example.domain.usecases.wallet.GetWalletUseCase
import org.example.domain.usecases.wallet.RestoreWalletUseCase
import org.example.domain.usecases.wallet.SearchWalletUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { CreateWalletUseCase(get(), get()) }
    single { GetWalletUseCase(get(), get()) }
    single { RestoreWalletUseCase(get(), get()) }
    single { RegisterUserUseCase(get(), get()) }
    single { SignInUseCase(get(), get()) }
    single { AddAccountToWalletUseCase(get(), get()) }
    single { SearchWalletUseCase(get(), get()) }

}