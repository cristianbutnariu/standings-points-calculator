package com.example.footballleaguepredictor.dataclasses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("standings")
data class LeagueStandings(
    @PrimaryKey(autoGenerate = true) @SerializedName("leagueStandingId") var leagueStandingsId: Int = 0,
    @Embedded @SerializedName("league") val leagueStandingsLeague: LeagueRanking,
)

data class LeagueRanking(
    @SerializedName("id") val leagueRankingId: Int,
    @SerializedName("name") val leagueRankingName: String,
    @SerializedName("country") val leagueRankingCountry: String,
    @SerializedName("logo") val leagueRankingLogo: String,
    @SerializedName("flag") val leagueRankingFlag: String? = null,
    @SerializedName("season") val leagueRankingSeason: Int,
    @SerializedName("standings")
    val leagueRankingStandings: List<List<FootballGroup>>? = null
)

//data class FootballGroupList(
//    @Embedded val leagueListTwo: List<List<FootballGroup>>
//)

data class FootballGroup(
    @SerializedName("rank") val footballGroupRank: Int,
    @SerializedName("team") val footballGroupTeam: Team,
    @SerializedName("points") val footballGroupPoints: Int,
    @SerializedName("goalsDiff") val footballGroupGoalsDiff: Int,
    @SerializedName("group") val footballGroupGroup: String,
    @SerializedName("form") val footballGroupForm: String,
    @SerializedName("status") val footballGroupStatus: String,
    @SerializedName("description") val footballGroupDescription: String? = null,
    @Embedded @SerializedName("all") val footballGroupAll: MatchRecord,
    @Embedded @SerializedName("home") val footballGroupHome: MatchRecord,
    @Embedded @SerializedName("away") val footballGroupAway: MatchRecord,
    @SerializedName("update") val footballGroupUpdate: String
)

data class Team(
    @SerializedName("id") val teamId: Int,
    @SerializedName("name") val teamName: String,
    @SerializedName("logo") val teamLogo: String
)

data class MatchRecord(
    @SerializedName("played") val matchRecordPlayed: Int,
    @SerializedName("win") val matchRecordWin: Int,
    @SerializedName("draw") val matchRecordDraw: Int,
    @SerializedName("lose") val matchRecordLose: Int,
    @Embedded @SerializedName("goals") val matchRecordGoals: Goals
)

data class Goals(
    @SerializedName("for") val goalsFor: Int,
    @SerializedName("against") val goalsAgainst: Int
)