package com.yaabelozerov.stats.data.remote.repository

import com.yaabelozerov.stats.data.remote.source.StatsApiImpl
import com.yaabelozerov.stats.domain.repository.StatsRepository
import retrofit2.await
import javax.inject.Inject

class StatsRepositoryImpl @Inject constructor(private val statsApiImpl: StatsApiImpl) :
    StatsRepository {
    override suspend fun getLangs(): Map<String, Double> {
        return mapOf(
            Pair("C++", 0.2),
            Pair("C#", 0.06),
            Pair("JS/TS", 0.32),
            Pair("Python", 0.4),
            Pair("Rust", 0.15),
            Pair("Go", 0.04),
        )

        val dto = statsApiImpl.getStatsForEvent(1).await()
        return mapOf(
            Pair("C++", dto.CpptPercentages),
            Pair("C#", dto.CsharpPercentages),
            Pair("Go", dto.GolangPercentages),
            Pair("JS/TS", dto.JSAndTSPercentages),
            Pair("Python", dto.PythonPercentages),
            Pair("Rust", dto.RustPercentages)
        )
    }

    override suspend fun getRoles(): Map<String, Double> {
        return mapOf(
            Pair("Фронтенд", 0.22),
            Pair("Бэкенд", 0.22),
            Pair("Фуллстек", 0.22),
            Pair("Мобильная разработка", 0.04),
            Pair("Анализ данных", 0.3)
        )

        val dto = statsApiImpl.getStatsForEvent(1).await()
        return mapOf(
            Pair("Фронтенд", dto.FrontendPercentages),
            Pair("Бэкенд", dto.BackendPercentages),
            Pair("Фуллстек", dto.FullStackPercentages),
            Pair("Мобильная разработка", dto.MobilePercentages),
            Pair("Анализ данных", dto.DataSciencePercentages))
    }
}