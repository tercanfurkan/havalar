package io.havalar.dao

import io.havalar.dao.DatabaseFactory.dbQuery
import io.havalar.models.CityWeather
import io.havalar.models.CityWeather.CityWeathers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like

class CityWeatherFacadeImpl : CityWeatherFacade {
    private fun resultRowToCityWeather(row: ResultRow) = CityWeather(
        cityName = row[CityWeathers.city],
        temperature = row[CityWeathers.temperature],
        windSpeedMs =  row[CityWeathers.windSpeedMs],
        windDirection = row[CityWeathers.windDirection]
    )
    override suspend fun all(): List<CityWeather> = dbQuery {
        CityWeathers.selectAll().orderBy(CityWeathers.city).map(::resultRowToCityWeather)
    }

    override suspend fun allWithPrefix(cityPrefix: String) = dbQuery {
        CityWeathers
            .select( CityWeathers.city.lowerCase().like("cityPrefix%".lowercase()))
            .map(::resultRowToCityWeather)
    }

    override suspend fun refreshAll(citiesAndWeathers: Set<CityWeather>): Unit = dbQuery {
        CityWeathers.deleteAll()
        CityWeathers.batchInsert(citiesAndWeathers, shouldReturnGeneratedValues = false) { (city, temperature, windSpeedMs, windDirection) ->
            this[CityWeathers.city] = city
            this[CityWeathers.temperature] = temperature
            this[CityWeathers.windSpeedMs] = windSpeedMs
            this[CityWeathers.windDirection] = windDirection
        }
    }

    override suspend fun deleteAll(): Unit = dbQuery {
        CityWeathers.deleteAll()
    }
}

val dao: CityWeatherFacade = CityWeatherFacadeImpl()