package org.example.di

import org.example.domain.usecase.wallet.CreateWalletUseCase
import org.example.domain.usecase.wallet.ImportWalletUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{CreateWalletUseCase(get(), get())}
    single{ImportWalletUseCase(get(), get())}
}