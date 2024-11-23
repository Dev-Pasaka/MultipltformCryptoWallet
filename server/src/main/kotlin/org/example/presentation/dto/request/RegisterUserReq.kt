package org.example.presentation.dto.request

import kotlinx.serialization.Serializable
import org.example.domain.entries.User

@Serializable
data class RegisterUserReq(
    val idempotencyKey: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String,
){
    fun toUser() = User(
        idempotencyKey = idempotencyKey,
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        password = password
    )
}
