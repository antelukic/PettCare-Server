package com.pettcare.auth.repository

import com.pettcare.auth.requests.AuthRequest
import com.pettcare.auth.requests.RegisterUser
import com.pettcare.auth.responses.AuthResponse
import com.pettcare.auth.security.token.TokenClaim
import com.pettcare.auth.security.token.TokenConfig
import com.pettcare.auth.security.token.TokenService
import com.pettcare.auth.service.UserService
import com.pettcare.auth.validators.EmailValidator
import com.pettcare.auth.validators.PasswordValidator
import com.pettcare.core.BaseResponse

private const val USER_ID_TOKEN_CLAIM = "userId"

class UserRepositoryImpl(
    private val userService: UserService,
    private val tokenService: TokenService,
    private val passwordValidator: PasswordValidator,
    private val emailValidator: EmailValidator
) : UserRepository {

    override suspend fun registerUser(params: RegisterUser, tokenConfig: TokenConfig): BaseResponse<Any> {
        try {
            if (doesEmailExist(params.email)) {
                return BaseResponse.ErrorResponse(message = "Email already registered")
            } else {

                if (emailValidator(params.email).not()) {
                    return BaseResponse.ErrorResponse(message = "Invalid email")
                }

                if (passwordValidator(params.password).not()) {
                    return BaseResponse.ErrorResponse(message = "Too weak password")
                }

                val user = userService.registerUser(params)
                return if (user != null) {
                    val token = tokenService.generate(
                        config = tokenConfig,
                        TokenClaim(
                            name = USER_ID_TOKEN_CLAIM,
                            value = user.id
                        )
                    )
                    user.authToken = token
                    BaseResponse.SuccessResponse(data = user)
                } else {
                    BaseResponse.ErrorResponse(message = "Couldn't register user")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return BaseResponse.ErrorResponse(message = "An error occurred, ${e.message}")
        }
    }

    override suspend fun loginUser(params: AuthRequest, tokenConfig: TokenConfig): BaseResponse<Any> {
        try {
            val user = userService.loginUser(params)
            return if (user == null) {
                BaseResponse.ErrorResponse(message = "Incorrect email or password")
            } else {
                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.id
                    )
                )
                BaseResponse.SuccessResponse(data = AuthResponse(token = token, id = user.id))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return BaseResponse.ErrorResponse(message = "An error occurred, ${e.message}")
        }
    }

    private suspend fun doesEmailExist(email: String): Boolean {
        return userService.findUserByEmail(email) != null
    }
}