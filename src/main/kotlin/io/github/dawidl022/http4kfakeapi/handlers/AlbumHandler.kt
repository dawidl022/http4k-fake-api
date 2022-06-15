package io.github.dawidl022.http4kfakeapi.handlers

import io.github.dawidl022.http4kfakeapi.models.Album
import io.github.dawidl022.http4kfakeapi.models.Albums
import org.http4k.core.Method.*
import org.http4k.routing.bind
import org.http4k.routing.routes

val lensAlbum = makeLens<Album>()
val lensAlbumList = makeLens<List<Album>>()

fun AlbumHandler() = routes(
    "" bind GET to {
        lensAlbumList.respond(Albums.all())
    }
)
