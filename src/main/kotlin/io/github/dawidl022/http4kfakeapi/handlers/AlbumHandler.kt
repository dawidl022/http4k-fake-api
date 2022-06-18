package io.github.dawidl022.http4kfakeapi.handlers

import io.github.dawidl022.http4kfakeapi.models.Album
import io.github.dawidl022.http4kfakeapi.models.Albums
import org.http4k.core.Method.*
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

val lensAlbum = makeLens<Album>()
val lensAlbumList = makeLens<List<Album>>()

fun AlbumHandler() = routes(
    "" bind GET to {
        lensAlbumList.respond(Albums.all())
    },
    // TODO handle int conversion errors
    "/{id}" bind GET to {
        val album = Albums.get(it.path("id")?.toInt() ?: 0)
        if (album != null)
            lensAlbum.respond(album)
        else
            Response(Status.NOT_FOUND)
    }
)
