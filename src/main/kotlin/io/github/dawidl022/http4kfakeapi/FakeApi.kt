package io.github.dawidl022.http4kfakeapi

import io.github.dawidl022.http4kfakeapi.formats.JacksonMessage
import io.github.dawidl022.http4kfakeapi.formats.JacksonXmlMessage
import io.github.dawidl022.http4kfakeapi.formats.jacksonMessageLens
import io.github.dawidl022.http4kfakeapi.formats.jacksonXmlMessageLens
import io.github.dawidl022.http4kfakeapi.graphql.UserDbHandler
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.graphQL
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/formats/xml" bind GET to {
        Response(OK).with(jacksonXmlMessageLens of JacksonXmlMessage("Barry", "Hello there!"))
    },

    "/formats/json/jackson" bind GET to {
        Response(OK).with(jacksonMessageLens of JacksonMessage("Barry", "Hello there!"))
    },

    "/testing/hamkrest" bind GET to {request ->
        Response(OK).body("Echo '${request.bodyString()}'")
    },

    "/graphql" bind graphQL(UserDbHandler())
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(Netty(9000)).start()

    println("Server started on " + server.port())
}
