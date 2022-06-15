package io.github.dawidl022.http4kfakeapi

import io.github.dawidl022.http4kfakeapi.db.DatabaseFactory
import io.github.dawidl022.http4kfakeapi.handlers.RootHandler
import org.http4k.core.*
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

object Config {
    val dataDir = System.getenv("DATA_DIR")
}

fun main() {
    DatabaseFactory.init()

    val printingApp: HttpHandler = PrintRequest().then(RootHandler())

    val server = printingApp.asServer(Netty(8080)).start()

    println("Server started on " + server.port())
}
