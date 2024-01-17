package com.example.footballleaguepredictor.retrofit

import com.example.footballleaguepredictor.dataclasses.CountriesList
import com.example.footballleaguepredictor.dataclasses.LeagueStandingsList
import com.example.footballleaguepredictor.dataclasses.LeaguesList
import com.example.footballleaguepredictor.dataclasses.MatchList
import com.example.footballleaguepredictor.dataclasses.SeasonsList
import com.example.footballleaguepredictor.dataclasses.TeamsList
import retrofit2.Response

class DataSourceImplementation(private val service: APIInterface) : DataSource {

    override suspend fun getAccountStatus(): Response<String> {
        return service.getAccountStatus()
    }

    override suspend fun getCountries(): Response<CountriesList> {
        return service.getCountries()
    }

    override suspend fun getLeague(): Response<LeaguesList> {
        return service.getLeague()
    }

    override suspend fun getStandings(id: Int, year: Int): Response<LeagueStandingsList> {
        return service.getStandings(id, year)
    }

    override suspend fun getFixtures(id: Int, year: Int): Response<MatchList> {
        return service.getFixtures(id, year)
    }

    override suspend fun getSeasons(): Response<SeasonsList> {
        return service.getSeasons()
    }

    override suspend fun getTeams(id: Int, year: Int): Response<TeamsList> {
        return service.getTeams(id, year)
    }


}