package com.richmeat.data.model


import io.ktor.auth.Principal

data class Login(
    val userName: String,
    val password: String
) : Principal




