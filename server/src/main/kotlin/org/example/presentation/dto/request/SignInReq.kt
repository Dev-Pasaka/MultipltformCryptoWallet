package org.example.presentation.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInReq(
    val email: String,
    val password: String
)