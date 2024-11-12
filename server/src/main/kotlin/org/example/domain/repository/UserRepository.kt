package org.example.domain.repository

interface UserRepository {
    suspend fun signIn()
}