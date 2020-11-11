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
                call.respondText("Hello World all ok Ariandi is my girl!", ContentType.Text.Plain)
            }


            post("richmeat/modules") {
                try {
                    val productivity = Gson().fromJson(call.receive<String>(), Array<Login>::class.java)
                    call.respond(HttpStatusCode.Created)

                } catch (e: Exception) {
                    call.respond(e.printStackTrace())
                }


            }



            get("/richmeat/users") {
                call.respond(gson.toJson(userService.getAllUsers()))
            }
            get("/richmeat/productivity") {
            }

//            post("richmeat/login") {
//                val userLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
//                dataBaseService.loginRquest(userLogin)
//                call.respond(HttpStatusCode.Created)
//            }

        }

    }

    server.start(wait = true)
}







