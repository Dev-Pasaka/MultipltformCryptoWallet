package org.example.di

import org.example.common.utils.TransactionChannel
import org.koin.dsl.module

val channelModule = module {
    single{TransactionChannel}
}