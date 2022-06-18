package io.github.dawidl022.http4kfakeapi.handlers

import io.github.dawidl022.http4kfakeapi.models.Album
import io.github.dawidl022.http4kfakeapi.models.Albums
import io.github.dawidl022.http4kfakeapi.models.util.Idable
import io.github.dawidl022.http4kfakeapi.models.util.SeedableTable
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.http4k.lens.BiDiBodyLens
import org.http4k.routing.path

class ResourceHandler<T: Idable>(private val table: SeedableTable<T>, private val lens: BiDiBodyLens<T>) {
    private val lensList = makeLens<List<T>>()

    fun all(): Response =
        lensList.respond(table.all())

    fun get(request: Request): Response {
        val id = getRequestId(request) ?: return badIdFormatResponse()
        val record = table.get(id)
        return if (record != null)
            lens.respond(record)
        else
            return noRecordWithIdResponse(id)
    }

    fun post(request: Request): Response {
        val newRecord = lens.extract(request)
        return if (table.add(newRecord))
            successResponse("Added")
        else
            failureResponse("add")
    }

    fun put(request: Request): Response {
        val id = getRequestId(request) ?: return badIdFormatResponse()
        if (Albums.get(id) == null) {
            return noRecordWithIdResponse(id)
        }

        val updatedRecord = lens.extract(request)
        return if (table.put(id, updatedRecord))
            successResponse("Updated", id)
        else
            failureResponse("update")
    }

    fun delete(request: Request): Response {
        val id = getRequestId(request) ?: return badIdFormatResponse()
        if (Albums.get(id) == null) {
            return noRecordWithIdResponse(id)
        }
        return if (Albums.delete(id))
            successResponse("Deleted", id, Status.OK)
        else
            failureResponse("delete")
    }

    private fun getRequestId(request: Request): Int? = request.path("id")?.toIntOrNull()

    private fun badIdFormatResponse(): Response =
        Response(Status.BAD_REQUEST)
            .body("${{table.name.replaceFirstChar(Char::uppercase)}} id must be an integer")

    private fun noRecordWithIdResponse(id: Int): Response =
        Response(Status.NOT_FOUND).body("No ${table.name} with id $id")

    private fun successResponse(action: String, id: Int? = null, status: Status = Status.CREATED) =
        Response(status).body("$action ${table.name}${if (id != null) " with id $id" else ""}")

    private fun failureResponse(action: String) =
        Response(Status.INTERNAL_SERVER_ERROR).body("Failed to $action ${table.name}")
}

fun <T> BiDiBodyLens<T>.respond(obj: T): Response = this.inject(obj, Response(Status.OK))
// also works:
// Response(Status.OK).with(this of obj)

inline fun <reified T: Any> makeLens(): BiDiBodyLens<T> = Body.auto<T>().toLens()
