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
    },
    "" bind POST to {
        val newAlbum = lensAlbum.extract(it)
        if (Albums.add(newAlbum))
            Response(Status.CREATED)
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to add album")
    },
    "/{id}" bind PUT to {
        val id = it.path("id")?.toInt() ?: 0
        if (Albums.get(id) == null) {
            return@to Response(Status.NOT_FOUND).body("No album found with id $id")
        }
        val updatedAlbum = lensAlbum.extract(it)
        if (Albums.put(id, updatedAlbum))
            Response(Status.CREATED)
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to update album")
    },
    "/{id}" bind DELETE to {
        if (Albums.delete(it.path("id")?.toInt() ?: 0))
            Response(Status.NO_CONTENT)
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to delete album")
    },
)
