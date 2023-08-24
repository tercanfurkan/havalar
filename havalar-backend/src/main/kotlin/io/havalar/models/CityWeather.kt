package io.havalar.models

import io.havalar.models.CityWeather.CityWeathers.nullable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class CityWeather(val cityName: String, val temperature: Float?, val windSpeedMs: Float?, val windDirection: Float?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityWeather

        return cityName == other.cityName
    }

    override fun hashCode(): Int {
        return cityName.hashCode()
    }

    object CityWeathers : IntIdTable() {
        val city = varchar("city", 128).index()
        val temperature = float("temperature").nullable()
        val windSpeedMs = float("wind_speed_ms").nullable()
        val windDirection = float("wind_direction").nullable()
        val lastUpdated = varchar("last_updated", 128).nullable()
    }
}