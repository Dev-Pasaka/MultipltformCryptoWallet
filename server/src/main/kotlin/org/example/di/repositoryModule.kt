package org.example.di


import org.example.data.repository.CircleRepositoryImpl
import org.example.data.repository.RequestLinkRepositoryImpl
import org.example.data.repository.TransactionRepositoryImpl
import org.example.data.repository.UserRepositoryImpl
import org.example.data.repository.WalletRepositoryImpl
import org.example.domain.repository.CircleRepository
import org.example.domain.repository.EncryptionRepository
import org.example.domain.repository.EncryptionRepositoryImpl
import org.example.domain.repository.RequestLinkRepository
import org.example.domain.repository.TransactionRepository
import org.example.domain.repository.UserRepository
import org.example.domain.repository.WalletRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<EncryptionRepository> {
        EncryptionRepositoryImpl()
    }
    single<CircleRepository> {
        CircleRepositoryImpl(get(), get(), get())
    }
    single<UserRepository> {
        UserRepositoryImpl(get(), lazy { get<WalletRepository>() }, get())
    }
    single<WalletRepository> {
        WalletRepositoryImpl(get(), get(), get(), lazy { get<UserRepository>() })
    }
    single<TransactionRepository>{
        TransactionRepositoryImpl(get(), get(), get())
    }
    single<RequestLinkRepository>{
        RequestLinkRepositoryImpl(get())
    }
}