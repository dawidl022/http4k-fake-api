package io.github.dawidl022.http4kfakeapi.routes

import io.github.dawidl022.http4kfakeapi.handlers.ResourceHandler
import io.github.dawidl022.http4kfakeapi.handlers.makeLens
import io.github.dawidl022.http4kfakeapi.models.Photos
import org.http4k.routing.RoutingHttpHandler

fun PhotoRoutes(): RoutingHttpHandler {
    val handler = ResourceHandler(Photos, makeLens())
    return ResourceRoutes(handler)
}
