package io.github.dawidl022.http4kfakeapi.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes

fun RootHandler() = routes(
    "album" bind AlbumHandler()
//    "/graphql" bind graphQL(UserDbHandler())
)
