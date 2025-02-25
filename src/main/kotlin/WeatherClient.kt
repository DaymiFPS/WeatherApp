package com.daymi

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object WeatherClient {
    private const val BASE_URL = "https://api.open-meteo.com/v1" //Basis URL für open-meteo

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getWeather(latitude: Float, longitude: Float): WeatherResponse? {
        return try {
            client.get("${BASE_URL}/forecast") {
                parameter("latitude", latitude)
                parameter("longitude", longitude)
                parameter("hourly", "temperature_2m")
                parameter("timezone", "Europe/Berlin")
            }.body()
        } catch (e: Exception) {
            println("Fehler beim Abrufen des Wetters: ${e.message}")
            null
        }
    }

    suspend fun getCoordinates(city: String): GeocodingResult? {
        return try {
            val response = client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", city)
                parameter("count", "1")
                parameter("language", "de")
                parameter("format", "json")
            }.body<GeocodingResponse>()
            response.results.firstOrNull()
        } catch (e: Exception) {
            println("Fehler beim Finden der Stadt: ${e.message}")
            null
        }
    }
}