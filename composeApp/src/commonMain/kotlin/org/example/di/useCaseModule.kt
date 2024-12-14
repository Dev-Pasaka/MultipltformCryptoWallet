package org.example.di

import org.example.domain.usecase.wallet.CreateWalletUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{CreateWalletUseCase(get())}
}