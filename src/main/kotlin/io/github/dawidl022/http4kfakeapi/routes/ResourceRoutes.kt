package io.github.dawidl022.http4kfakeapi.routes

import io.github.dawidl022.http4kfakeapi.handlers.ResourceHandler
import io.github.dawidl022.http4kfakeapi.models.util.Idable
import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

fun <T : Idable> ResourceRoutes(handler: ResourceHandler<T>): RoutingHttpHandler =
    routes(
        "" bind Method.GET to {
            handler.all()
        },
        "/{id}" bind Method.GET to {
            handler.get(it)
        },
        "" bind Method.POST to {
            handler.post(it)
        },
        "/{id}" bind Method.PUT to {
            handler.put(it)
        },
        "/{id}" bind Method.DELETE to {
            handler.delete(it)
        },
    )
