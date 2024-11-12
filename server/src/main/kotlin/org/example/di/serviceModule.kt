package org.example.di

import org.example.presentation.service.WalletService
import org.koin.dsl.module

val serviceModule = module {
    single { WalletService(get(), get(), get()) }
}