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
    "/{id}" bind GET to {
        val id = it.path("id")?.toIntOrNull() ?:
            return@to Response(Status.BAD_REQUEST).body("Album id must be an integer")
        val album = Albums.get(id)
        if (album != null)
            lensAlbum.respond(album)
        else
            Response(Status.NOT_FOUND).body("No album with id $id")
    },
    "" bind POST to {
        val newAlbum = lensAlbum.extract(it)
        if (Albums.add(newAlbum))
            Response(Status.CREATED).body("Added album")
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to add album")
    },
    "/{id}" bind PUT to {
        val id = it.path("id")?.toIntOrNull() ?:
            return@to Response(Status.BAD_REQUEST).body("Album id must be an integer")
        if (Albums.get(id) == null) {
            return@to Response(Status.NOT_FOUND).body("No album with id $id")
        }
        val updatedAlbum = lensAlbum.extract(it)
        if (Albums.put(id, updatedAlbum))
            Response(Status.CREATED).body("Updated album with id $id")
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to update album")
    },
    "/{id}" bind DELETE to {
        val id = it.path("id")?.toIntOrNull() ?:
        return@to Response(Status.BAD_REQUEST).body("Album id must be an integer")
        if (Albums.get(id) == null) {
            return@to Response(Status.NOT_FOUND).body("No album with id $id")
        }
        if (Albums.delete(id))
            Response(Status.NO_CONTENT).body("Deleted album with id $id")
        else
            Response(Status.INTERNAL_SERVER_ERROR).body("Failed to delete album")
    },
)
