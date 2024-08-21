package com.yaabelozerov.user.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO (
    @Json(name="id") var id: Int? = null,
    @Json(name="name") var name: String? = null,
    @Json(name="surname") var surname: String? = null,
    @Json(name="patronymic") var patronymic: String? = null,
    @Json(name="email") var email: String? = null,
    @Json(name="photo") var photo: String? = null,
    @Json(name="tg_username") var tgUsername: String? = null,
    @Json(name="is_admin") var isAdmin: Boolean? = null,
    @Json(name="role") var role: String? = null,
    @Json(name="tags") var tags: List<String>? = null,
    @Json(name="langs") var langs: List<String>? = null,
)