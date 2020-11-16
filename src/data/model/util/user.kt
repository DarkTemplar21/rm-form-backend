package data.model.util

import com.richmeat.data.model.Login
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.login get() = authentication.principal<Login>().toString()