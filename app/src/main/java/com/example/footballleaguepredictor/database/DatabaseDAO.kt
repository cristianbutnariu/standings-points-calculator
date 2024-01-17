package com.example.footballleaguepredictor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData

@Dao
@TypeConverters(Converters::class)
interface DatabaseDAO {
    @Query("select * from countries")
    suspend fun getCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCountries(countries: List<Country>)

    @Query("select * from leagues where countryName =:name")
    suspend fun getLeagues(name: String): List<Leagues>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLeagues(leagues: List<Leagues>)

    @Query("select * from standings where leagueRankingId=:id and leagueRankingSeason=:year")
    suspend fun getStandings(id: Int, year: Int): List<LeagueStandings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setStandings(standings: List<LeagueStandings>)

    @Query("select * from matches where leagueTypeId=:id and leagueTypeSeason=:year")
    suspend fun getFixtures(id: Int, year: Int): List<Match>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFixtures(fixtures: List<Match>)

    @Query("select * from seasons")
    suspend fun getSeasons(): List<Seasons>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSeasons(seasons: List<Seasons>)

    @Query("select * from teams where leagueId=:id and season=:year")
    suspend fun getTeams(id: Int, year: Int): List<TeamData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTeams(teams: List<TeamData>)

}