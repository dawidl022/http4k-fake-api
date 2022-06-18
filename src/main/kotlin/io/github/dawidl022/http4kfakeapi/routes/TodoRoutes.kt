package io.github.dawidl022.http4kfakeapi.routes

import io.github.dawidl022.http4kfakeapi.handlers.ResourceHandler
import io.github.dawidl022.http4kfakeapi.handlers.makeLens
import io.github.dawidl022.http4kfakeapi.models.Todos
import org.http4k.routing.RoutingHttpHandler

fun TodoRoutes(): RoutingHttpHandler {
    val handler = ResourceHandler(Todos, makeLens())
    return ResourceRoutes(handler)
}
