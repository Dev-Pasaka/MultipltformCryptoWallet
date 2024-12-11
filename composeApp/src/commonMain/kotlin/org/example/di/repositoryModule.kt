package org.example.di

import org.example.data.repository.KeyValueStorageImpl
import org.example.domain.KeyValueStorage
import org.koin.dsl.module

val repositoryModule = module{
    single<KeyValueStorage>{KeyValueStorageImpl(get())}
}