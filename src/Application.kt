package com.richmeat

import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.richmeat.data.DataBaseService
import com.richmeat.data.form.FormDTO
import com.richmeat.data.form.FormService
import com.richmeat.data.model.Login
import com.richmeat.data.model.PrintRequest
import com.richmeat.data.model.user.LoginToken
import com.richmeat.data.model.user.UserService
import com.richmeat.data.util.PrintingUtil
import data.model.JwtConfig
import com.richmeat.data.util.login
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
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

                var text =
                    "get /authenticate -Recibe el Bearer token como header y devuelve el usuario y contraseña para pruebas\n" +
                            "get /users -Devuelve los usuarios\nget /forms-Devuelve los formularios de temperatura\n" +
                            "post /form -Recibe un formulario y lo introduce en base de datos Retorna Codigo 201-Created\n" +
                            "get /forms -Devuelve los formularios de temperatura\n" +
                            "post /sign_up -Recibe un usuario {\"userName\":\"ariandi\",\"password\":\"ariandi\"}para registro Retorna Codigo 201-Created\n" +
                            "post /login -Recibe un usuario para logearse {\"userName\":\"ariandi\",\"password\":\"ariandi\"} Retorna Codigo 201-Created"
                call.respondText(text, ContentType.Text.Plain)
            }
            authenticate {
                //get /authenticate-Recibe el Bearer token como heather y devuelve el usuario y contraseña para pruebas
                get("/richmeat/authenticate") {
                    call.respond("User is: ${call.login?.userName} Passoword is: ${call.login?.password}")
                }
                //get /users-Devuelve los usuarios
                get("/richmeat/users") {
                    call.respond(gson.toJson(userService.getAllUsers()))
                }
                //get /forms-Devuelve los formularios de temperatura
                get("/richmeat/forms") {
                    val userName = call.login?.userName
                    call.respond(gson.toJson(formService.getAllUserForms(userName!!)))
                }
                //get /forms-Devuelve un formulario de temperatura enviandole un id
                post("/richmeat/form_by_id") {
                    val userName = call.login?.userName
                    val formId = Gson().fromJson(call.receive<String>(), Int::class.java)
                    call.respond(gson.toJson(formService.getUserFormById(userName!!,formId)))
                }
                //get /forms-Devuelve un listado de impresoras
                get("/richmeat/printers_list") {
                    val userName = call.login?.userName
                    call.respond(gson.toJson(PrintingUtil.getPrinterList()))
                }


                //post /form-Recibe un formulario y lo introduce en base de datos Retorna Codigo 201-Created
                post("/richmeat/form") {
                    val userName = call.login?.userName ?: "usuarioNoEncontrado"
                    val newForm = Gson().fromJson(call.receive<String>(), FormDTO::class.java)
                    formService.insertForm(newForm, userName)
                    call.respond(HttpStatusCode.Created)
                }
                //get /forms-Peticion de impresion Recive nombre de impresora e id del form
                post("/richmeat/print") {
                    val userName = call.login?.userName ?: "usuarioNoEncontrado"
                    val printRequest = Gson().fromJson(call.receive<String>(), PrintRequest::class.java)
                    val temperatureForm = formService.getUserFormById(userName, printRequest.formId)
                    PrintingUtil.main(temperatureForm, printRequest.numberOfCopies)
                    call.respond(HttpStatusCode.Created)
                }

            }

            //post /sign_up-Recibe un usuario {"userName":"ariandi","password":"ariandi"}para registro Retorna Codigo 201-Created
            post("/richmeat/sign_up") {
                val newUserLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                if (dataBaseService.createUser(newUserLogin)) {

                    val loginToken = LoginToken(JwtConfig.generateToken(newUserLogin),
                        userService.getUserRole(newUserLogin.userName))
                    call.respond(HttpStatusCode.Created,gson.toJson(loginToken) )
                } else {
                    call.respond(HttpStatusCode.NotAcceptable)
                }
            }
            //post /login-Recibe un usuario para logearse {"userName":"ariandi","password":"ariandi"} Retorna Codigo 201-Created
            post("/richmeat/login") {
                val userLogin = Gson().fromJson(call.receive<String>(), Login::class.java)
                var loginExists = dataBaseService.loginExists(userLogin)
                if (loginExists) {
                    val loginToken = LoginToken(JwtConfig.generateToken(userLogin),
                        userService.getUserRole(userLogin.userName))

                    call.respond(HttpStatusCode.Created,gson.toJson(loginToken) )

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










