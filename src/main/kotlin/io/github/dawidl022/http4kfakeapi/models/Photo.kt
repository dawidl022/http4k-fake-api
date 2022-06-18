package io.github.dawidl022.http4kfakeapi.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.dawidl022.http4kfakeapi.Config
import io.github.dawidl022.http4kfakeapi.models.util.Idable
import io.github.dawidl022.http4kfakeapi.models.util.SeedableTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.io.File

data class Photo(override val id: Int?, val albumId: Int, val title: String, val url: String, val thumbnailUrl: String)
    : Idable


object Photos : SeedableTable<Photo>("photo") {
    override val id = integer("id").autoIncrement()
    val albumId = integer("album_id")
    val title = varchar("title", 255)
    val url = varchar("url", 1023)
    val thumbnailUrl = varchar("thumbnail_url", 1023)

    override fun seed(): List<Photo> =
        jacksonObjectMapper().readValue(File(Config.dataDir + "photos.json"))

    override fun fromRow(row: ResultRow) =
        Photo(
            id = row[id],
            albumId = row[albumId],
            title = row[title],
            url = row[url],
            thumbnailUrl = row[thumbnailUrl],
        )

    override fun <Key : Any> builderSchema(builder: UpdateBuilder<Key>, item: Photo) {
        builder[albumId] = item.albumId
        builder[title] = item.title
        builder[url] = item.url
        builder[thumbnailUrl] = item.thumbnailUrl
    }
}
