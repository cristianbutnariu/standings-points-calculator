package com.example.footballleaguepredictor.retrofit

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData
import retrofit2.Response


object Repository {
    private lateinit var dataSource: DataSource
    private lateinit var dbDataSource: LocalDataSource
    private lateinit var application: Application
    private lateinit var sharedPref: SharedPreferences

    operator fun invoke(
        remoteDataSource: DataSource,
        localDataSource: LocalDataSource,
        application: Application
    ): Repository {
        this.dataSource = remoteDataSource
        this.dbDataSource = localDataSource
        this.application = application
        sharedPref = application.getSharedPreferences("apiCallTiming", MODE_PRIVATE)
        return this
    }


    suspend fun getAccountStatus(): Response<String> {
        return dataSource.getAccountStatus()
    }

    suspend fun getCountries(): List<Country> {
        if (System.currentTimeMillis() - sharedPref.getLong("countriesLastCall", 0) >= 86400000L) {
            val source = dataSource.getCountries()
            Log.d("getCountriesCheck", source.isSuccessful.toString())
            if (source.isSuccessful) {
                dbDataSource.setCountries(source.body()!!.countriesList)
                sharedPref.edit().putLong("countriesLastCall", System.currentTimeMillis()).apply()
                sharedPref.edit().putBoolean("countriesSuccess", true).apply()
            } else {
                sharedPref.edit().putBoolean("countriesSuccess", false).apply()
            }
        }
        return dbDataSource.getCountries()
    }

    suspend fun getLeague(name: String): List<Leagues> {
        if (System.currentTimeMillis() - sharedPref.getLong("leagueLastCall", 0) >= 3600000L) {
            val source = dataSource.getLeague()
            Log.d("getLeagueCheck", source.body().toString())
            if (source.isSuccessful) {
                dbDataSource.setLeague(source.body()!!.leaguesList)
                sharedPref.edit().putLong("leagueLastCall", System.currentTimeMillis()).apply()
                sharedPref.edit().putBoolean("leaguesSuccess", true).apply()
            } else {
                sharedPref.edit().putBoolean("leaguesSuccess", false).apply()
            }
        }
        return dbDataSource.getLeague(name)
    }

    suspend fun getStandings(id: Int, year: Int): List<LeagueStandings> {
        if (System.currentTimeMillis() - sharedPref.getLong("standingsLastCall${id + year}", 0) >= 3600000L) {
            val source = dataSource.getStandings(id, year)
            if (source.isSuccessful) {
                dbDataSource.setStandings(source.body()!!.leaguesStandingsList)
                sharedPref.edit().putLong("standingsLastCall${id + year}", System.currentTimeMillis()).apply()
                sharedPref.edit().putBoolean("standingsSuccess${id + year}", true).apply()
            } else {
                sharedPref.edit().putBoolean("standingsSuccess${id + year}", false).apply()
            }
        }
        return dbDataSource.getStandings(id, year)
    }

    suspend fun getFixtures(id: Int, year: Int): List<Match> {
        if (System.currentTimeMillis() - sharedPref.getLong(
                "fixturesLastCall${id + year}",
                0
            ) >= 86400000L
        ) {
            val source = dataSource.getFixtures(id, year)
            if (source.isSuccessful) {
                dbDataSource.setFixtures(source.body()!!.matchList)
                sharedPref.edit()
                    .putLong("fixturesLastCall${id + year}", System.currentTimeMillis()).apply()
                sharedPref.edit().putBoolean("fixturesSuccess${id + year}", true).apply()
            } else {
                sharedPref.edit().putBoolean("fixturesSuccess${id + year}", false).apply()
            }
        }
        return dbDataSource.getFixtures(id, year)
    }

    suspend fun getSeasons(): List<Seasons> {
        if (System.currentTimeMillis() - sharedPref.getLong("seasonsLastCall", 0) >= 86400000L) {
            val source = dataSource.getSeasons()

            if (source.isSuccessful) {
                dbDataSource.setSeasons(
                    source.body()!!.seasonsList
                        .mapIndexed { index, year -> Seasons(id = index + 1, season = year) }
                        .filter { it.season <= 2023 })
                sharedPref.edit().putLong("seasonsLastCall", System.currentTimeMillis()).apply()
                sharedPref.edit().putBoolean("seasonsSuccess", true).apply()
            } else {
                sharedPref.edit().putBoolean("seasonsSuccess", false).apply()
            }
        }
        return dbDataSource.getSeasons()
    }

    suspend fun getTeams(id: Int, year: Int): List<TeamData> {
        if (System.currentTimeMillis() - sharedPref.getLong(
                "teamsLastCall${id + year}", 0
            ) >= 86400000L
        ) {
            val source = dataSource.getTeams(id, year)
            val teamsList = source.body()!!.teamsList
            if (source.isSuccessful) {
                for (team in teamsList) {
                    team.leagueId = id
                    team.season = year
                }
                dbDataSource.setTeams(teamsList)
                sharedPref.edit().putLong("teamsLastCall${id + year}", System.currentTimeMillis())
                    .apply()
                sharedPref.edit().putBoolean("teamsSuccess${id + year}", true).apply()
            } else {
                sharedPref.edit().putBoolean("teamsSuccess${id + year}", false).apply()
            }
        }
        return dbDataSource.getTeams(id, year)
    }


}