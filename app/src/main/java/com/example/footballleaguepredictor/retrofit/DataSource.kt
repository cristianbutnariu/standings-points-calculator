package com.example.footballleaguepredictor.retrofit

import com.example.footballleaguepredictor.dataclasses.CountriesList
import com.example.footballleaguepredictor.dataclasses.LeagueStandingsList
import com.example.footballleaguepredictor.dataclasses.LeaguesList
import com.example.footballleaguepredictor.dataclasses.MatchList
import com.example.footballleaguepredictor.dataclasses.SeasonsList
import com.example.footballleaguepredictor.dataclasses.TeamsList
import retrofit2.Response

interface DataSource {
    suspend fun getAccountStatus(): Response<String>

    suspend fun getCountries(): Response<CountriesList>

    suspend fun getLeague(): Response<LeaguesList>

    suspend fun getStandings(id: Int, year: Int): Response<LeagueStandingsList>

    suspend fun getFixtures(id: Int, year: Int): Response<MatchList>

    suspend fun getSeasons(): Response<SeasonsList>

    suspend fun getTeams(id: Int, year: Int): Response<TeamsList>
}