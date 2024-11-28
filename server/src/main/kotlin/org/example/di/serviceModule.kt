package org.example.di

import org.example.presentation.service.TransactionService
import org.example.presentation.service.UserService
import org.example.presentation.service.WalletService
import org.koin.dsl.module

val serviceModule = module {
    single { WalletService(get(), get(), get(), get(), get()) }
    single { UserService(get(), get(), get()) }
    single { TransactionService(get(), get(), get()) }
}