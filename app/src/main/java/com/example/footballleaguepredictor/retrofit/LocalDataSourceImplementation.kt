package com.example.footballleaguepredictor.retrofit

import com.example.footballleaguepredictor.database.AppDatabase
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData

class LocalDataSourceImplementation(private val database: AppDatabase) : LocalDataSource {

    override suspend fun getCountries(): List<Country> {
        return database.roomDao().getCountries()
    }

    override suspend fun setCountries(countries: List<Country>) {
        return database.roomDao().setCountries(countries)
    }

    override suspend fun getLeague(name: String): List<Leagues> {
        return database.roomDao().getLeagues(name)
    }

    override suspend fun setLeague(league: List<Leagues>) {
        return database.roomDao().setLeagues(league)
    }

    override suspend fun getStandings(id: Int, year: Int): List<LeagueStandings> {
        return database.roomDao().getStandings(id, year)
    }

    override suspend fun setStandings(standings: List<LeagueStandings>) {
        return database.roomDao().setStandings(standings)
    }

    override suspend fun getFixtures(id: Int, year: Int): List<Match> {
        return database.roomDao().getFixtures(id, year)
    }

    override suspend fun setFixtures(fixtures: List<Match>) {
        return database.roomDao().setFixtures(fixtures)
    }

    override suspend fun getSeasons(): List<Seasons> {
        return database.roomDao().getSeasons()
    }

    override suspend fun setSeasons(seasons: List<Seasons>) {
        return database.roomDao().setSeasons(seasons)
    }

    override suspend fun getTeams(id: Int, year: Int): List<TeamData> {
        return database.roomDao().getTeams(id, year)
    }

    override suspend fun setTeams(teams: List<TeamData>) {
        return database.roomDao().setTeams(teams)
    }

}