package io.havalar.dao

import io.havalar.fetchWeatherData
import io.havalar.models.CityWeather.CityWeathers
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    suspend fun init(config: ApplicationConfig) {
        val jdbcURL = config.property("storage.jdbcURL").getString()
        val driverClassName = config.property("storage.driverClassName").getString()
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.drop(CityWeathers)
            SchemaUtils.create(CityWeathers)
        }
        dbQuery { dao.refreshAll(fetchWeatherData()) }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}