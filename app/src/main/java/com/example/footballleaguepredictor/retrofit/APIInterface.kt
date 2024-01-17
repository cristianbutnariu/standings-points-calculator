package com.example.footballleaguepredictor.retrofit

import com.example.footballleaguepredictor.dataclasses.CountriesList
import com.example.footballleaguepredictor.dataclasses.LeagueStandingsList
import com.example.footballleaguepredictor.dataclasses.LeaguesList
import com.example.footballleaguepredictor.dataclasses.MatchList
import com.example.footballleaguepredictor.dataclasses.SeasonsList
import com.example.footballleaguepredictor.dataclasses.TeamsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("https://v3.football.api-sports.io/status")
    suspend fun getAccountStatus(): Response<String>

    @GET("https://v3.football.api-sports.io/countries")
    suspend fun getCountries(): Response<CountriesList>

    @GET("https://v3.football.api-sports.io/leagues")
    suspend fun getLeague(): Response<LeaguesList>

    @GET("https://v3.football.api-sports.io/standings")
    suspend fun getStandings(
        @Query("league") id: Int,
        @Query("season") year: Int
    ): Response<LeagueStandingsList>

    @GET("https://v3.football.api-sports.io/fixtures")
    suspend fun getFixtures(
        @Query("league") id: Int,
        @Query("season") year: Int
    ): Response<MatchList>

    @GET("https://v3.football.api-sports.io/leagues/seasons")
    suspend fun getSeasons(): Response<SeasonsList>

    @GET("https://v3.football.api-sports.io/teams")
    suspend fun getTeams(
        @Query("league") id: Int,
        @Query("season") year: Int
    ): Response<TeamsList>

}