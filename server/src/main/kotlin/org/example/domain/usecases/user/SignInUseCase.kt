package org.example.domain.usecases.user

import io.ktor.http.HttpStatusCode
import org.example.common.utils.ServerConfig
import org.example.domain.repository.UserRepository
import org.example.presentation.dto.response.SignInRes

class SignInUseCase(
    private val userRepository: UserRepository,
    private val serverConfig: ServerConfig
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = try {
        userRepository.signIn(email, password)
    }catch (e: Exception){
        e.printStackTrace()
        serverConfig.logger.warn("Error occurred while signing in user: ${e.message}")
        SignInRes(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            status = false,
            message = "Error occurred while signing in user"
        )
    }

}