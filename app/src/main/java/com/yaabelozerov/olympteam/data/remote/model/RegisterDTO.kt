package com.yaabelozerov.olympteam.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterDTO (
    @Json(name="name") val name: String,
    @Json(name="surname") val surname: String,
    @Json(name="patronymic") val patronymic: String,
    @Json(name="email") val email: String,
    @Json(name="password") val password: String,
    @Json(name="photo") val photo: String,
    @Json(name="tg_username") val tgUsername: String,
    @Json(name="is_admin") val isAdmin: Boolean,
    @Json(name="role") val role: String,
    @Json(name="tags") val tags: List<String>,
)