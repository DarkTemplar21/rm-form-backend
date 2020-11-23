package com.richmeat.data.model.user

import org.jetbrains.exposed.sql.Column
import java.net.PasswordAuthentication
import java.util.*

data class User(
    val userName: String,
    val role: String,
    val password:  String
)
// request object
data class UserDTO(
    val userName: String,
    val role: String,
    val password: String
)