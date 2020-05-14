package com.richmeat.data.model.user

import org.jetbrains.exposed.sql.Column
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val dni: Long,
    val exitsCount: String


)
// request object
data class UserDTO(
    val firstName: String,
    val lastName: String,
    val dni: Long,
    val exitsCount: String

)