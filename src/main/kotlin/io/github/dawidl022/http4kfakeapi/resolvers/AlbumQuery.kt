package io.github.dawidl022.http4kfakeapi.resolvers

import io.github.dawidl022.http4kfakeapi.models.Album
import io.github.dawidl022.http4kfakeapi.models.Albums
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AlbumQuery {
    fun albums(): List<Album> =
        Albums.all()

    fun album(id: Int): Album =
        Albums.get(id)

    fun albumsByUser(userId: Int): List<Album> =
        transaction {
            Albums.select {
                Albums.userId eq userId
            }.map(Albums::fromRow)
        }
}
