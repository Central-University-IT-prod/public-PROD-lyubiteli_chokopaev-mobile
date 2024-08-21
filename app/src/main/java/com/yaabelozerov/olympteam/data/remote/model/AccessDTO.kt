package com.yaabelozerov.olympteam.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessDTO(
    @Json(name="access_token")
    val token: String?
)