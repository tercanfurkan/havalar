package io.havalar.dao

import io.havalar.models.CityWeather

interface CityWeatherFacade {
    suspend fun all(): List<CityWeather>
    suspend fun allWithPrefix(cityPrefix: String): List<CityWeather>
    suspend fun refreshAll(citiesAndWeathers: Set<CityWeather>)
    suspend fun deleteAll()
}