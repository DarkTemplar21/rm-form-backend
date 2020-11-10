package com.richmeat.data.model.user

import org.jetbrains.exposed.sql.Column
import java.net.PasswordAuthentication
import java.util.*

data class User(
    val name: String,
    val email: String,
    val password:  String
)
// request object
data class UserDTO(
    val name: String,
    val email: String,
    val password: String


)