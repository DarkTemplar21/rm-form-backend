package com.richmeat

import com.google.gson.Gson
import com.richmeat.data.model.Productivity
import com.richmeat.data.model.ProductivityDTO
import com.richmeat.data.model.ProductivityService
import com.richmeat.data.model.user.UserDTO
import com.richmeat.data.model.user.UserService
import io.ktor.application.call
import io.ktor.http.ContentType
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
//    val database = initDB()
    UserService.DatabaseFactory.init()
    val userService = UserService()
    val productivityService = ProductivityService()
    val gson = Gson()

    val port = System.getenv("PORT")?.toInt() ?: 8080
    val server = embeddedServer(Netty, port = port) {
        routing {
            get("/richmeat") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/richmeat/users") {
                call.respond(gson.toJson(userService.getAllUsers()))
            }
            get("/richmeat/productivity") {
                call.respond(gson.toJson(productivityService.getProductivity()))


            }

            post("richmeat/user") {
                val userDto = Gson().fromJson(call.receive<String>(), UserDTO::class.java)
                userService.insertUser(userDto)
                call.respond(HttpStatusCode.Created)
            }
            post("richmeat/users") {
                val usersDto = Gson().fromJson(call.receive<String>(), Array<UserDTO>::class.java)
                userService.insertUsers(usersDto.toList())
                call.respond(HttpStatusCode.Created)
            }
            post("richmeat/productivity") {
                val productivity = Gson().fromJson(call.receive<String>(), Productivity::class.java)
                productivityService.insertProductivity(productivity)
                call.respond(HttpStatusCode.Created)
            }


        }
    }
    server.start(wait = true)
}





