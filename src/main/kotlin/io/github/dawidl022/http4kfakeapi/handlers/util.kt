package io.github.dawidl022.http4kfakeapi.handlers

import org.http4k.core.Body
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.BiDiBodyLens


fun <T> BiDiBodyLens<T>.respond(obj: T): Response = this.inject(obj, Response(Status.OK))
// also works:
// Response(Status.OK).with(this of obj)


inline fun <reified T: Any> makeLens(): BiDiBodyLens<T> = Body.auto<T>().toLens()
