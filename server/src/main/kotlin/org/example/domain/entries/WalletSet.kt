package org.example.domain.entries


data class WalletSet(
    val id: String,
    val name: String,
    val custodyType: String,
    val createDate: String,
    val updateDate: String,
    val idempotencyKey: String
)