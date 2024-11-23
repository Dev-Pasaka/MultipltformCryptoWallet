package org.example.data.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import example.com.utils.JWTConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.example.common.utils.MongoDBConfig
import org.example.domain.entries.User
import org.example.domain.entries.Wallet
import org.example.domain.repository.EncryptionRepository
import org.example.domain.repository.UserRepository
import org.example.domain.repository.WalletRepository
import org.example.presentation.dto.request.AddAccountToWalletReq
import org.example.presentation.dto.response.AddAccountToWalletRes
import org.example.presentation.dto.response.SignInRes
import org.example.presentation.dto.response.CreateUseRes
import org.example.presentation.dto.response.Data
import org.example.presentation.dto.response.RegisterUserRes
import java.util.Date

class UserRepositoryImpl(
    private val db: MongoDBConfig,
    private val walletRepository: Lazy<WalletRepository>,
    private val encryptionRepository: EncryptionRepository
) : UserRepository {

    private val userCollection = db.database.getCollection<User>("user")
    private val walletCollection = db.database.getCollection<Wallet>("wallet")


    private fun generateToken(
        walletId: String
    ): String {
        val token = JWT.create()
            .withAudience(JWTConfig.jwtAudience)
            .withIssuer(JWTConfig.jwtDomain)
            .withClaim("walletId", walletId)
            .withExpiresAt(Date(System.currentTimeMillis() + (60000 * 3600)))
            .sign(HMAC256(JWTConfig.jwtSecret))

        return token
    }

    override suspend fun createUser(user: User): CreateUseRes = withContext(Dispatchers.IO) {
        val doesUserExist = userCollection.find(
            Filters.eq(User::username.name, user.username)
        ).toList()
        if (doesUserExist.isNotEmpty()) return@withContext CreateUseRes(
            status = false,
            message = "User already exists"
        )
        val result = userCollection.insertOne(user).wasAcknowledged()
        if (result) return@withContext CreateUseRes(
            status = false,
            message = "User creation failed"
        )
        return@withContext CreateUseRes(status = true, message = "User created successfully")
    }

    override suspend fun register(user: User): RegisterUserRes = withContext(Dispatchers.IO) {
        val doesUserExist = userCollection.find(
            Filters.or(
                Filters.eq(User::username.name, user.username),
                Filters.eq(User::email.name, user.email),
            )
        ).toList()
        if (doesUserExist.isNotEmpty()) return@withContext RegisterUserRes(
            status = false,
            message = "User already exists"
        )

        val hashedPassword: String? = user.password?.let { encryptionRepository.hashPassword(it) }

        val wallet = walletRepository.value.createWallet(user.idempotencyKey)
        if (!wallet.status) return@withContext RegisterUserRes(
            status = false,
            message = "Wallet creation failed"
        )

        val result = userCollection.insertOne(
            user.copy(
                password = hashedPassword,
                walletId = wallet.secrets?.id ?: ""

            )
        ).wasAcknowledged()

        if (!result) return@withContext RegisterUserRes(
            status = false,
            message = "User creation failed"
        )



        return@withContext RegisterUserRes(
            status = true,
            message = "User registered successfully",
            data = Data(
                secretsRes = wallet.secrets,
                wallet = wallet.wallet,
            )
        )
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): SignInRes = withContext(Dispatchers.IO) {
        val user = userCollection.find(Filters.eq(User::email.name, email)).firstOrNull()
            ?: return@withContext SignInRes(
                status = false,
                message = "Invalid email or password"
            )
        val isPasswordValid =
            encryptionRepository.verifyHashedPassword(password, user.password ?: "")
        if (!isPasswordValid) return@withContext SignInRes(
            status = false,
            message = "Invalid email or password"
        )

        val token = generateToken(
            walletId = user.walletId
        )
        return@withContext SignInRes(
            status = true,
            message = "User signed in successfully",
            token = token
        )


    }

    override suspend fun addAccountToWallet(
        user: AddAccountToWalletReq,
        walletId: String
    ): AddAccountToWalletRes =
        withContext(
            Dispatchers.IO
        ) {
            val doesUserExist = userCollection.find(
                Filters.or(
                    Filters.eq(User::username.name, user.username),
                    Filters.eq(User::email.name, user.email),
                )
            ).toList()
            if (doesUserExist.isNotEmpty()) return@withContext AddAccountToWalletRes(
                status = false,
                message = "User already exists"
            )

            val getWallet =
                walletCollection.find(Filters.eq(Wallet::id.name, walletId)).firstOrNull()
                    ?: return@withContext AddAccountToWalletRes(
                        status = false,
                        message = "Wallet not found"
                    )

            val hashedPassword: String? =
                user.password?.let { encryptionRepository.hashPassword(it) }
            val result = userCollection.findOneAndUpdate(
                Filters.eq(User::walletId.name, walletId),
                Updates.combine(
                    Updates.set(User::username.name, user.username),
                    Updates.set(User::firstName.name, user.firstName),
                    Updates.set(User::lastName.name, user.lastName),
                    Updates.set(User::email.name, user.email),
                    Updates.set(User::phone.name, user.phone),
                    Updates.set(User::password.name, hashedPassword),
                )
            )
            if (result == null) return@withContext AddAccountToWalletRes(
                status = false,
                message = "User creation failed"
            )
            val token = generateToken(
                walletId = walletId
            )
            return@withContext AddAccountToWalletRes(
                status = true,
                message = "User added to wallet successfully",
                token = token
            )

        }
}