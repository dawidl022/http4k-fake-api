package io.github.dawidl022.http4kfakeapi.routes

import org.http4k.routing.bind
import org.http4k.routing.routes

fun RootRoutes() = routes(
    "album" bind AlbumRoutes(),
    "photo" bind PhotoRoutes(),
    "todo" bind TodoRoutes(),
//    "/graphql" bind graphQL(UserDbHandler())
)
