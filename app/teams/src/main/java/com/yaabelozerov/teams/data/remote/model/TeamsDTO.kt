package com.yaabelozerov.teams.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamsDTO (
    @Json(name = "id")
    var id: Int? = null,

    @Json(name = "author_id")
    var authorId: Int? = null,

//    @Json(name = "event_id")
//    var eventId: Int? = null

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "size")
    var size: Int? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "need")
    var need: List<String>? = null,

    @Json(name = "tags")
    var tags: List<String>? = null,

    @Json(name = "members")
    var members: List<Int>? = null
)