package org.example.domain.usecases.user

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.entries.User
import org.example.domain.repository.UserRepository
import org.example.presentation.dto.response.CreateUseRes
import org.example.presentation.dto.response.RegisterUserRes

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(user: User): RegisterUserRes = try {
        userRepository.register(user)
    } catch (e: Exception) {
        e.printStackTrace()
        serverConfig.logger.warn("User registration failed: ${e.message}")
        RegisterUserRes(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            status = false,
            message = "User registration failed"
        )

    }
}