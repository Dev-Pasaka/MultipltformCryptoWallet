package org.example.di


import org.example.data.repository.CircleRepositoryImpl
import org.example.data.repository.WalletRepositoryImpl
import org.example.domain.repository.CircleRepository
import org.example.domain.repository.EncryptionRepository
import org.example.domain.repository.EncryptionRepositoryImpl
import org.example.domain.repository.WalletRepository
import org.koin.dsl.module

val  repositoryModule = module {
    single<EncryptionRepository> {EncryptionRepositoryImpl()}
    single<CircleRepository> {CircleRepositoryImpl(get(), get(), get())}
    single<WalletRepository>{WalletRepositoryImpl(get(), get(), get())}
}