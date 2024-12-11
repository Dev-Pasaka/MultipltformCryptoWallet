package org.example.domain

interface KeyValueStorage {
    suspend fun putString(key: String, value: String): Boolean
    suspend fun getString(key: String): String?
}