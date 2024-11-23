package org.example.presentation.controller

import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.util.reflect.typeInfo
import org.example.presentation.dto.request.AddAccountToWalletReq
import org.example.presentation.dto.request.RegisterUserReq
import org.example.presentation.dto.request.SignInReq
import org.example.presentation.dto.response.AddAccountToWalletRes
import org.example.presentation.dto.response.RegisterUserRes
import org.example.presentation.dto.response.SignInRes
import org.example.presentation.service.UserService

fun Route.userController(userService: UserService){
    route("/user"){
        post {
            val user = call.receive<RegisterUserReq>()
            val register = userService.registerUser(user)
            call.respond(
                typeInfo = typeInfo<RegisterUserRes>(),
                message = register
            )
        }

        post("/sign-in"){
            val user = call.receive<SignInReq>()
            val signIn = userService.signIn(user.email, user.password)
            call.respond(
                typeInfo = typeInfo<SignInRes>(),
                message = signIn
            )
        }
        post("/add-account-to-wallet") {
            val user = call.receive<AddAccountToWalletReq>()
            val walletId = call.request.queryParameters["walletId"] ?: return@post call.respond(
                typeInfo = typeInfo<RegisterUserRes>(),
                message = RegisterUserRes(
                    status = false,
                    message = "Wallet id is required"
                )
            )
            val addAccountToWallet = userService.addAccountToWallet(user, walletId)
            call.respond(
                typeInfo = typeInfo<AddAccountToWalletRes>(),
                message = addAccountToWallet
            )

        }

    }
}