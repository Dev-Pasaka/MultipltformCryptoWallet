package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class AddAccountToWalletReq(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null,
)
