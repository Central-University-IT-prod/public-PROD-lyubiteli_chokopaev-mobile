package com.yaabelozerov.stats.domain.repository

interface StatsRepository {
    suspend fun getLangs(): Map<String, Double>
    suspend fun getRoles(): Map<String, Double>
}