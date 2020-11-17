package com.richmeat

import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.richmeat.data.DataBaseService
import com.richmeat.data.form.Form
import com.richmeat.data.form.FormDTO
import com.richmeat.data.form.FormService
import com.richmeat.data.model.Login
import com.richmeat.data.model.user.UserService
import data.model.JwtConfig
import data.model.util.login
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.Compiler.enable

fun main(args: Array<String>) {

    UserService.DatabaseFactory.init()
    val userService = UserService()
    val dataBaseService = DataBaseService()
    val formService = FormService()
    val gson = Gson()

    val port = System.getenv("PORT")?.toInt() ?: 8080
    val server = embeddedServer(Netty, port = port) {

        install(CORS) {

            method(method = HttpMethod("GET"))
            method(method = HttpMethod("POST"))
            method(method = HttpMethod("PUT"))
            header("richmeat")
            anyHost()


        }
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }


        install(Authentication) {
            jwt {
                verifier(JwtConfig.verifier)
                realm = "com.imran"
                validate {
                    val userName = it.payload.getClaim("userName").toString()
                    val password = it.payload.getClaim("password").toString()
                    val login = Login(userName, password)
                    if (dataBaseService.loginExists(login)) {
                        print("login exists")
                        login
                    } else {
                        print("login dont exists exists")
                        null
                    }

                }

            }
        }


        routing {
            get("/hi") {
                call.respondText("Hello World all ok", ContentType.Text.Plain)
            }

                get("/authenticate") {
                    val jwt =
                        JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsInBhc3N3b3JkIjoiYWxlIiwiaXNzIjoiY29tLmltcmFuIiwibmFtZSI6ImFsZSIsImV4cCI6MTYwNTcwNzEzNX0.gj7MKqbjbKOPETh4lcbnQGsCZVg1YgRCujbSyiiSsrYSJmIUacX148tR6qdI-UV1vwLCcfii2fUxzHTShmaAHw")
                    val user = jwt.getClaim("userName")
                    val pass = jwt.getClaim("password")
                    call.respond(
                        "get authenticated value from token " +
                                "name = ${call.authentication.principal<Login>()}, password= ${call.login?.password.toString()}"
                    )
                    call.respond(
                        "get authenticated value from token " +
                                "name = ${Gson().fromJson(
                                    call.login.toString(),
                                    Login::class.java
                                )}, password= ${call.login?.password.toString()}"
                    )
                }
                get("/richmeat/users") {
                    call.respond(gson.toJson(userService.getAllUsers()))
                }

                get("/richmeat/forms") {
                    call.respond(gson.toJson(formService.getAllForms()))
                }

                post("/richmeat/form"){
                    val newForm = Gson().fromJson(call.receive<String>(), FormDTO::class.java)
                    formService.insertForm(newForm)
                }
            



            post("/richmeat/login") {
                val userLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                var loginExists = dataBaseService.loginExists(userLogin)
                if (loginExists) {
                    val token = JwtConfig.generateToken(userLogin)
                    call.respond(HttpStatusCode.OK, "Token:$token")

                }
                call.respond(HttpStatusCode.Unauthorized)
            }


//            post("/richmeat/generate_token") {
//                val login = Gson().fromJson(call.receive<String>(), Login::class.java)
//                print("${login.userName} , pwd= ${login.password}")
//                val token = JwtConfig.generateToken(login)
//                call.respond(token)
//            }

        }


    }





    server.start(wait = true)
}







