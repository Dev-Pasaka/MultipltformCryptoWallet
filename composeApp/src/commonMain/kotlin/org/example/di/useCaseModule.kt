package org.example.di

import org.example.domain.usecase.requestLink.CreateRequestLinkUseCase
import org.example.domain.usecase.requestLink.GetRequestLinkDataUseCase
import org.example.domain.usecase.user.GetSessionStatusUseCase
import org.example.domain.usecase.wallet.CreateWalletUseCase
import org.example.domain.usecase.wallet.GetTransactionUseCase
import org.example.domain.usecase.wallet.GetWalletUseCase
import org.example.domain.usecase.wallet.ImportWalletUseCase
import org.example.domain.usecase.wallet.TransferCryptoUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{CreateWalletUseCase(get(), get())}
    single{ImportWalletUseCase(get(), get())}
    single{GetWalletUseCase(get(), get())}
    single{GetSessionStatusUseCase(get())}
    single{ CreateRequestLinkUseCase(get())}
    single{ GetRequestLinkDataUseCase(get())}
    single{ TransferCryptoUseCase(get())}
    single{ GetTransactionUseCase(get(), get())}
}