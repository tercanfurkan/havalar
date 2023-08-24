package io.havalar.plugins

import io.havalar.dao.CityWeatherFacadeImpl
import io.havalar.dao.dao
import io.havalar.fetchWeatherData
import io.havalar.models.CityWeather
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

data class WeatherData(val citiesAndWeathers: Set<CityWeather>)

private val json = Json { allowSpecialFloatingPointValues = true }

fun Application.configureRouting() {
    routing {
        staticResources("/", "client")
        get("/all") {
            val citiesAndWeathers = dao.all()
            val jsonString = json.encodeToJsonElement(citiesAndWeathers)
            call.respond(json.encodeToString(jsonString))
        }
    }
}
