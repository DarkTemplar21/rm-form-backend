package com.richmeat

import com.google.gson.Gson
import com.richmeat.data.DataBaseService
import com.richmeat.data.form.Form
import com.richmeat.data.form.FormService
import com.richmeat.data.model.Login
import com.richmeat.data.model.user.UserService
import data.model.JwtConfig
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

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
        install(Authentication) {
            jwt {
                verifier(JwtConfig.verifier)
                realm = "com.imran"
                validate {
                    val userName = it.payload.getClaim("name").toString()
                    val password = it.payload.getClaim("password").toString()
                    if (!userName.isEmpty()){
                        print("login exists")
                        Login(userName, password)
                    }else{
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
                    call.respond("all ok auth valid")
                }
            }

            get("/richmeat/users") {
                call.respond(gson.toJson(userService.getAllUsers()))
            }

            get("/richmeat/forms") {
                call.respond(gson.toJson(formService.getAllForms()))
            }


            post("/richmeat/login") {
                val userLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                var loginExists = dataBaseService.loginExists(userLogin)
                call.respond(HttpStatusCode.Created, "login:" + loginExists)
            }
            post("/richmeat/forms") {
                val newform = Gson().fromJson(call.receive<String>(), Form::class.java)
                formService.insertForm(newform)
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







