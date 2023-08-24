package io.havalar

import io.havalar.dao.DatabaseFactory
import io.havalar.plugins.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() = runBlocking {
    DatabaseFactory.init(environment.config)
    configureOpenApiDocs()
    configureSerialization()
    configureRouting()
}
