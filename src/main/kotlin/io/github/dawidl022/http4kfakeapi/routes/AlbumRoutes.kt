package io.github.dawidl022.http4kfakeapi.routes

import io.github.dawidl022.http4kfakeapi.handlers.ResourceHandler
import io.github.dawidl022.http4kfakeapi.handlers.makeLens
import io.github.dawidl022.http4kfakeapi.models.Albums
import org.http4k.core.Method.*
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes


fun AlbumRoutes(): RoutingHttpHandler {
    val handler = ResourceHandler(Albums, makeLens())
    return ResourceRoutes(handler)
}
