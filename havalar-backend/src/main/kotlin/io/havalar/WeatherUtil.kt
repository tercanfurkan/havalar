package io.havalar
import io.havalar.models.CityWeather
import kotlinx.serialization.Serializable
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun fetchWeatherData(): Set<CityWeather> {
    val baseUrl = "http://opendata.fmi.fi/wfs"

    val startTime = LocalDateTime.now(ZoneOffset.UTC).minusSeconds(120 * 60 + 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))

    val params = mapOf(
        "request" to "getFeature",
        "storedquery_id" to "fmi::observations::weather::cities::timevaluepair",
        "parameters" to "temperature,windspeedms,winddirection",
        "starttime" to startTime,
        "timestep" to "10"
    )

    val url = URL(baseUrl + "?" + params.entries.joinToString("&") { "${it.key}=${it.value}" })
    println("URL: $url")
    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream())
    val memberNodes = document.getElementsByTagName("wfs:member")
    val distinctCitiesAndWeathers = mutableSetOf<CityWeather>()

    for (i in 0 until memberNodes.length step 3) {
        val temperatureNode = memberNodes.item(i) as Element
        val windspeedNode = memberNodes.item(i+1) as Element
        val winddirectionNode = memberNodes.item(i+2) as Element
        val cityName = temperatureNode.getElementsByTagName("target:region").item(0).textContent
        val temperature = temperatureNode.getElementsByTagName("wml2:value").item(0).textContent.toFloatOrNull()?.takeIf { it.isFinite() }
        val windSpeedMs = windspeedNode.getElementsByTagName("wml2:value").item(0).textContent.toFloatOrNull()?.takeIf { it.isFinite() }
        val windDirection = winddirectionNode.getElementsByTagName("wml2:value").item(0).textContent.toFloatOrNull()?.takeIf { it.isFinite() }
        distinctCitiesAndWeathers.add(CityWeather(cityName, temperature, windSpeedMs, windDirection))
    }

    return distinctCitiesAndWeathers
}

fun main() {
    val cityWeatherList = fetchWeatherData()
    println(cityWeatherList.count())

    for (cityWeather in cityWeatherList) {
        print("City: ${cityWeather.cityName} ")
        print("Temperature: ${cityWeather.temperature}°C ")
        print("Windspeed: ${cityWeather.windSpeedMs} m/s ")
        println("Winddirection: ${cityWeather.windDirection}°")
    }
}
