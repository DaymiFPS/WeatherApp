package com.daymi

import kotlinx.coroutines.runBlocking

fun main() {
    println("Willkommen zur Wetter App!")

    while (true) {
        print("Geben Sie den Breitengrad ein (oder 'exit' zum Beenden): ")
        val latInput = readLine()?.trim() ?: ""
        if (latInput.lowercase() == "exit") break

        print("Geben Sie den Längengrad ein: ")
        val lonInput = readLine()?.trim() ?: ""

        if (latInput.isNotEmpty() && lonInput.isNotEmpty()) {
            try {
                val latitude = latInput.toFloat()
                val longitude = lonInput.toFloat()

                runBlocking {
                    val weather = WeatherClient.getWeather(latitude, longitude)
                    if (weather != null) {
                        val latestTime = weather.hourly.time.first()
                        val latestTemp = weather.hourly.temperature_2m.first()
                        println("Das Wetter um ${latestTime.removeRange(0,latestTime.length-5)} Uhr: $latestTemp Grad Celsius")
                    } else {
                        println("Konnte das Wetter nicht abrufen.")
                    }
                }
            } catch (e: NumberFormatException) {
                println("Bitte geben Sie gültige Zahlen für Breiten- und Längengrad ein.")
            }
        } else {
            println("Bitte geben Sie sowohl Breiten- als auch Längengrad ein.")
        }
    }
    println("Auf Wiedersehen!")
}