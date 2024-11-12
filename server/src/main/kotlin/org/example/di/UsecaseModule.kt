package org.example.di


import org.example.domain.usecases.wallet.CreateWalletUseCase
import org.example.domain.usecases.wallet.GetWalletUseCase
import org.example.domain.usecases.wallet.RestoreWalletUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { CreateWalletUseCase(get(), get()) }
    single { GetWalletUseCase(get(), get()) }
    single { RestoreWalletUseCase(get(), get()) }

}