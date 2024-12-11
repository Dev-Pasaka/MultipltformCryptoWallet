package org.example.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.data.local.DATA_STORE_FILE_NAME
import org.example.data.local.createDataStore
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin( dataStore: DataStore<Preferences>? = null, config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            repositoryModule,
            useCaseModule,
            viewModelsModule,
            org.example.di.config,
            module {
                single{
                    dataStore ?: createDataStore {
                        DATA_STORE_FILE_NAME
                    }
                }
            }
        )
    }
}