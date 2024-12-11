package org.example.di

import org.example.data.local.DATA_STORE_FILE_NAME
import org.example.data.local.createDataStore
import org.koin.dsl.module

val config = module {
    single{
        createDataStore {
            DATA_STORE_FILE_NAME
        }
    }
}