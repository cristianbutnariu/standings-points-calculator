package com.example.footballleaguepredictor.retrofit

import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData

interface LocalDataSource {
    suspend fun getCountries(): List<Country>

    suspend fun setCountries(countries: List<Country>)

    suspend fun getLeague(name: String): List<Leagues>

    suspend fun setLeague(league: List<Leagues>)

    suspend fun getStandings(id: Int, year: Int): List<LeagueStandings>

    suspend fun setStandings(standings: List<LeagueStandings>)

    suspend fun getFixtures(id: Int, year: Int): List<Match>

    suspend fun setFixtures(fixtures: List<Match>)

    suspend fun getSeasons(): List<Seasons>

    suspend fun setSeasons(seasons: List<Seasons>)

    suspend fun getTeams(id: Int, year: Int): List<TeamData>

    suspend fun setTeams(teams: List<TeamData>)
}