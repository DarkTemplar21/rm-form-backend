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
import com.richmeat.data.util.login
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.OAuthAccessTokenResponse
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
                    val userName = it.payload.getClaim("userName").asString()
                    val password = it.payload.getClaim("password").asString()
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
                authenticate {
                get("/authenticate") {
                    call.respond("User is: ${call.login?.userName} Passoword is: ${call.login?.password}")
                       

                }

            }
            get("/richmeat/users") {
                call.respond(gson.toJson(userService.getAllUsers()))
            }

            get("/richmeat/forms") {
                call.respond(gson.toJson(formService.getAllForms()))
            }

            post("/richmeat/form") {
                val newForm = Gson().fromJson(call.receive<String>(), FormDTO::class.java)
                formService.insertForm(newForm)
                call.respond(HttpStatusCode.Created)
            }
            post("/richmeat/sign_up") {
                val newUserLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                dataBaseService.createUser(newUserLogin)
                call.respond(HttpStatusCode.Created)
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


            post("/richmeat/generate_token") {
                val login = Gson().fromJson(call.receive<String>(), Login::class.java)
                print("${login.userName} , pwd= ${login.password}")
                val token = JwtConfig.generateToken(login)
                call.respond(token)
            }

        }


    }





    server.start(wait = true)
}







