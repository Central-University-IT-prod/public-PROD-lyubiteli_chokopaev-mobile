package com.yaabelozerov.user.domain.model

data class UserData(
    val id: Int? = null,
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val email: String? = null,
    val isAdmin: Boolean? = null,
    val avatarUrl: String? = null,
    val tags: List<String>? = null,
    val langs: List<String>? = null,
    val role: String? = null,
    val tgId: String? = null,
    val teamId: Int? = null,
    val userAccess: String = "LOGGEDOUT"
)