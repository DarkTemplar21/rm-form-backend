package com.richmeat

import com.google.gson.Gson
import com.richmeat.data.model.DataBaseService
import com.richmeat.data.model.user.Login
import com.richmeat.data.model.user.UserService
import io.ktor.application.call
import io.ktor.application.install
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
        routing {



            get("/hi") {
                call.respondText("Hello World all ok", ContentType.Text.Plain)
            }

            get("/richmeat/users") {
                call.respond(gson.toJson(userService.getAllUsers()))
            }


            post("/richmeat/login") {
                val userLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                var loginSuccess = dataBaseService.loginRquest(userLogin)
                call.respond(HttpStatusCode.Created.toString()+ "login:"+loginSuccess)
            }

        }

    }

    server.start(wait = true)
}







