package io.havalar.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*

fun Application.configureOpenApiDocs() {
    routing {
        openAPI(path = "openapi")
    }
}
