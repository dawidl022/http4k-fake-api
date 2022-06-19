package io.github.dawidl022.http4kfakeapi.routes

import io.github.dawidl022.http4kfakeapi.graphql.ApiGraphQLHandler
import org.http4k.routing.bind
import org.http4k.routing.graphQL
import org.http4k.routing.routes

fun RootRoutes() = routes(
    "/graphql" bind graphQL(ApiGraphQLHandler())
)
