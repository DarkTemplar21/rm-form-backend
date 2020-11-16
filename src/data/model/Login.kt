package com.richmeat.data.model

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.Principal

data class Login(
    val userName: String,
    val password: String
) : Principal {





    companion object {
        fun buildJwtVerifier() =
            JWT.require(Algorithm.HMAC256("secretKey123"))
                .withIssuer("secretKey123")
                .build()

        fun generateToken(login: Login): String = JWT.create()
            .withSubject("Authentication")
            .withIssuer("secretKey123")
            .withClaim("userName", login.userName)
            .withClaim("password", login.password)
            .sign(Algorithm.HMAC256("secretKey123"))
    }




}




