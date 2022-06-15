package io.github.dawidl022.http4kfakeapi.formats

import org.http4k.core.Body
import org.http4k.format.Jackson.auto

data class JacksonMessage(val subject: String, val message: String)

val jacksonMessageLens = Body.auto<JacksonMessage>().toLens()
