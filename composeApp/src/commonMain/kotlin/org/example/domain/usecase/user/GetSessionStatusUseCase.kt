package org.example.domain.usecase.user

import org.example.common.Constants
import org.example.domain.repository.KeyValueStorage

class GetSessionStatusUseCase(
    private val keyValueStorage: KeyValueStorage
) {
    suspend operator fun invoke(): Boolean {
        val session = keyValueStorage.getString(Constants.WALLET_ID)
        println("Session: $session")
        return session != null
    }

}