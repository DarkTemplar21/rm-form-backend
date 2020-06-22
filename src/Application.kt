package com.richmeat

import com.google.gson.Gson
import com.richmeat.data.model.Module
import com.richmeat.data.model.ProductivityService
import com.richmeat.data.model.user.UserDTO
import com.richmeat.data.model.user.UserService
import com.richmeat.data.model.util.ProductivityAndroid
import com.richmeat.data.model.util.ProductivityConverter
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.request.document
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun main(args: Array<String>) {

//    UserService.DatabaseFactory.init()
    val userService = UserService()
    val productivityService = ProductivityService()
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



            get("/richmeat/productivity/date/*") {
                getProductivitiesByDateResponse(productivityService, gson)
            }
            get("/richmeat/productivity/accumulated/*") {
                getAccumulatedProductivityREsponse(productivityService, gson)
            }



            post("richmeat/modules") {
                val productivity = Gson().fromJson(call.receive<String>(), Array<Module>::class.java)
                productivityService.insertProductivity(productivity.toList())
                call.respond(HttpStatusCode.Created)

            }



            //not Used endpoints
            post("richmeat/productivity") {
                val productivity = Gson().fromJson(call.receive<String>(), Array<Module>::class.java)
//                productivityService.insertProductivity(productivity)
                call.respond(HttpStatusCode.Created)
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
        }

    }

    server.start(wait = true)
}

private suspend fun PipelineContext<Unit, ApplicationCall>.getProductivitiesByDateResponse(
    productivityService: ProductivityService,
    gson: Gson
) {
    val dateString = call.request.document()
    val turn = dateString.split("-")[0].toInt()
    val newDateTime = converOldFormatDateToNewOne(dateString, turn)
    val productivitiesKtor = productivityService.getProductivityByDate(newDateTime, turn)
    var productivitiesAndroid = mutableListOf<ProductivityAndroid>()
    productivitiesKtor.forEach {
        productivitiesAndroid.add(ProductivityConverter.fromProductivityKtorToProductivityAndroid(it))
    }
    call.respond(gson.toJson(productivitiesAndroid))
}

private suspend fun PipelineContext<Unit, ApplicationCall>.getAccumulatedProductivityREsponse(
    productivityService: ProductivityService,
    gson: Gson
) {
    val request = call.request.document()
    val startDateString = request.split("..")[0]
    val endDateString = request.split("..")[1]
    val startDate = converOldFormatDateToNewOne(startDateString, turn = 3)
    val endDate = converOldFormatDateToNewOne(endDateString, 3)
    val productivityKtor = productivityService.getAccumulatedProductivity(startDate, endDate)
    val productivityAndroid =
        ProductivityConverter.fromProductivityKtorToProductivityAndroid(productivityKtor)
    call.respond(gson.toJson(productivityAndroid))
}

private fun converOldFormatDateToNewOne(
    dateString: String,
    turn: Int
): DateTime {
    val mOldDateFormat = "dd-MM-yyyy"
    val mDateFormat = "yyyy-MM-dd"
    val formaterOld = DateTimeFormat.forPattern(mOldDateFormat)
    val formaterNew = DateTimeFormat.forPattern(mDateFormat)
    var date = dateString
    if (dateString.split("-").size == 4){
       date = dateString.replaceFirst("$turn-", "")
    }
    val dateTime = DateTime.parse(date, formaterOld)
    val newFormatDate = dateTime.toString(mDateFormat)
    val newDateTime = DateTime.parse(newFormatDate, formaterNew)
    return newDateTime
}





