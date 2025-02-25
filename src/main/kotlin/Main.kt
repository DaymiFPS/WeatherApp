package com.daymi

import kotlinx.coroutines.runBlocking

fun main() {
    println("Willkommen zur Wetter-App!")
    while (true) {
        print("Gib den Städtenamen ein (oder 'exit' zum Beenden): ") // Fragt jetzt nach einer Stadt
        val city = readLine()?.trim() ?: ""
        if (city.lowercase() == "exit") break
        if (city.isNotEmpty()) {
            runBlocking {
                val location = WeatherClient.getCoordinates(city) // Holt Koordinaten aus der Stadt
                if (location != null) {
                    val weather = WeatherClient.getWeather(location.latitude, location.longitude)
                    if (weather != null) {
                        val latestTime = weather.hourly.time.first()
                        val latestTemp = weather.hourly.temperature_2m.first()
                        println("Aktuelles Wetter in ${location.name} um ${latestTime.removeRange(0,latestTime.length-5)} Uhr: $latestTemp°C")
                    } else {
                        println("Konnte das Wetter für $city nicht abrufen.")
                    }
                } else {
                    println("Stadt '$city' nicht gefunden.")
                }
            }
        } else {
            println("Bitte gib einen Städtenamen ein.")
        }
    }
    println("Auf Wiedersehen!")
}