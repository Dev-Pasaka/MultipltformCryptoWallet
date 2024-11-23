package org.example.presentation.dto.request

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable
import org.example.domain.entries.User

@Serializable
data class CreateUserReq(
    val idempotencyKey: String,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null,
) {

}
