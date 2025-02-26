package com.daymi

import kotlinx.serialization.Serializable // Umwandlung von JSON zu Kotlin Objekt

@Serializable // Damit ist die Klasse aus JSON konvertierbar
data class WeatherResponse( // "data class" erstellt automatisch nützliche Operationen wie get und set
    val hourly: Hourly,
)

@Serializable
data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Float>
)

@Serializable
data class GeocodingResponse(
    val results: List<GeocodingResult>
)

@Serializable
data class GeocodingResult(
    val latitude: Float,
    val longitude: Float,
    val name: String
)