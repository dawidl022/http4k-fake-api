package io.github.dawidl022.http4kfakeapi.models.util

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.dawidl022.http4kfakeapi.Config
import java.io.File

interface XMLParsable<M> {
    val data: M
}

object XMLParser {
    private val parser = XmlMapper(JacksonXmlModule().apply { setDefaultUseWrapper(false) })

    fun <M, T : XMLParsable<M>> parse(filePrefix: String, fileName: String, target: Class<T>): M =
        parser.readValue(
            File(filePrefix + fileName),
            target
        ).data

    fun <M, T : XMLParsable<M>> parse(fileName: String, target: Class<T>): M =
        parse(Config.dataDir, fileName, target)
}
