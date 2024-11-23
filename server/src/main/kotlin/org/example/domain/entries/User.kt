package org.example.domain.entries

import org.bson.types.ObjectId

data class User(
    val id: String = ObjectId().toString(),
    val idempotencyKey: String,
    val walletId: String = "",
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
)
