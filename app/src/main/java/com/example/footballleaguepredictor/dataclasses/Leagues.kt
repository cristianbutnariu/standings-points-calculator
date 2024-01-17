package com.example.footballleaguepredictor.dataclasses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.footballleaguepredictor.database.Converters
import com.google.gson.annotations.SerializedName

@Entity("leagues", indices = [Index(value = ["leagueId"], unique = true)])
@TypeConverters(Converters::class)
data class Leagues(
    @PrimaryKey(autoGenerate = true) @SerializedName("leaguesId") var id: Int = 0,
    @Embedded @SerializedName("league") var league: League,
    @Embedded @SerializedName("country") var country: Country,
    @SerializedName("seasons") var seasons: List<Season>? = null
)

data class League(
    @SerializedName("id") var leagueId: Int,
    @SerializedName("name") var leagueName: String,
    @SerializedName("type") var leagueType: String,
    @SerializedName("logo") var leagueLogo: String
)

//data class SeasonList(@Embedded var seasonList: List<Season>)

data class Season(
    @SerializedName("year") var seasonYear: Int,
    @SerializedName("start") var seasonStart: String,
    @SerializedName("end") var seasonEnd: String,
    @SerializedName("current") var seasonCurrent: Boolean,
    @Embedded @SerializedName("coverage") var seasonCoverage: Coverage
)

data class Coverage(
    @Embedded @SerializedName("fixtures") var coverageFixtures: Fixtures,
    @SerializedName("standings") var coverageStandings: Boolean,
    @SerializedName("players") var coveragePlayers: Boolean,
    @SerializedName("topScorers") var coverageTopScorers: Boolean,
    @SerializedName("topAssists") var coverageTopAssists: Boolean,
    @SerializedName("topCards") var coverageTopCards: Boolean,
    @SerializedName("injuries") var coverageInjuries: Boolean,
    @SerializedName("predictions") var coveragePredictions: Boolean,
    @SerializedName("odds") var coverageOdds: Boolean
)

data class Fixtures(
    @SerializedName("events") var fixturesEvents: Boolean,
    @SerializedName("lineups") var fixturesLineups: Boolean,
    @SerializedName("statisticsFixtures") var fixturesStatisticsFixtures: Boolean,
    @SerializedName("statisticsPlayers") var fixturesStatisticsPlayers: Boolean
)

