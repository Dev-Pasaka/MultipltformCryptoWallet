package org.example.di

import org.example.data.repository.KeyValueStorageImpl
import org.example.data.repository.RequestLinkRepositoryImpl
import org.example.data.repository.WalletRepositoryImpl
import org.example.domain.repository.KeyValueStorage
import org.example.domain.repository.RequestLinkRepository
import org.example.domain.repository.WalletRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<KeyValueStorage>{KeyValueStorageImpl(get())}
    single<WalletRepository>{ WalletRepositoryImpl(get())}
    single<RequestLinkRepository>{RequestLinkRepositoryImpl(get())}

}