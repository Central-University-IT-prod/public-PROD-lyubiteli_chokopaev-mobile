package com.yaabelozerov.stats.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatsDTO(
    @Json(name = "backendPercentages") val BackendPercentages: Double,
    @Json(name = "frontendPercentages") val FrontendPercentages: Double,
    @Json(name = "mobilePercentages") val MobilePercentages: Double,
    @Json(name = "fullStackPercentages") val FullStackPercentages: Double,
    @Json(name = "dataSciencePercentages") val DataSciencePercentages: Double,
    @Json(name = "pythonPercentages") val PythonPercentages: Double,
    @Json(name = "jSAndTSPercentages") val JSAndTSPercentages: Double,
    @Json(name = "golangPercentages") val GolangPercentages: Double,
    @Json(name = "csharpPercentages") val CsharpPercentages: Double,
    @Json(name = "rustPercentages") val RustPercentages: Double,
    @Json(name = "cpptPercentages") val CpptPercentages: Double,
)