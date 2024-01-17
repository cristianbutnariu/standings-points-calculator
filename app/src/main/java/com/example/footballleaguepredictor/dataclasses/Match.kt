package com.example.footballleaguepredictor.dataclasses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("matches", indices = [Index(value = ["fixtureId"], unique = true)])
data class Match(
    @PrimaryKey(autoGenerate = true) var matchId: Int,
    @Embedded @SerializedName("fixture") val matchFixture: Fixture,
    @Embedded @SerializedName("league") val matchLeague: LeagueType,
    @Embedded @SerializedName("teams") val matchTeams: Teams,
    @Embedded @SerializedName("goals") val matchGoals: MatchGoals,
    @Embedded @SerializedName("score") val matchScore: MatchScore
)

data class Fixture(
    @SerializedName("id") val fixtureId: Int?=null,
    @SerializedName("referee") val fixtureReferee: String? = null,
    @SerializedName("timezone") val fixtureTimezone: String?=null,
    @SerializedName("date") val fixtureDate: String?=null,
    @SerializedName("timestamp") val fixtureTimestamp: Long,
    @Embedded @SerializedName("periods") val fixturePeriods: Periods,
    @Embedded @SerializedName("venue") val fixtureVenue: Venue,
    @Embedded @SerializedName("status") val fixtureStatus: MatchStatus
)

data class Periods(
    @SerializedName("first") val periodsFirst: Long?=null,
    @SerializedName("second") val periodsSecond: Long?=null
)

data class Venue(
    @SerializedName("id") val venueId: Int?=null,
    @SerializedName("name") val venueName: String?=null,
    @SerializedName("city") val venueCity: String?=null
)

data class MatchStatus(
    @SerializedName("long") val matchStatusLong: String?=null,
    @SerializedName("short") val matchStatusShort: String?=null,
    @SerializedName("elapsed") val matchStatusElapsed: Int?=null
)

data class LeagueType(
    @SerializedName("id") val leagueTypeId: Int?=null,
    @SerializedName("name") val leagueTypeName: String?=null,
    @SerializedName("country") val leagueTypeCountry: String?=null,
    @SerializedName("logo") val leagueTypeLogo: String?=null,
    @SerializedName("flag") val leagueTypeFlag: String?=null,
    @SerializedName("season") val leagueTypeSeason: Int?=null,
    @SerializedName("round") val leagueTypeRound: String?=null
)

data class Teams(
    @Embedded @SerializedName("home") val teamHome: HomeTeam,
    @Embedded @SerializedName("away") val teamAway: AwayTeam
)

data class HomeTeam(
    @SerializedName("id") val footballTeamHomeId: Int?=null,
    @SerializedName("name") val footballTeamHomeName: String?=null,
    @SerializedName("logo") val footballTeamHomeLogo: String?=null,
    @SerializedName("winner") val footballTeamHomeWinner: Boolean?=null
)

data class AwayTeam(
    @SerializedName("id") val footballTeamAwayId: Int?=null,
    @SerializedName("name") val footballTeamAwayName: String?=null,
    @SerializedName("logo") val footballTeamAwayLogo: String?=null,
    @SerializedName("winner") val footballTeamAwayWinner: Boolean?=null
)

data class MatchScore(
    @Embedded @SerializedName("halftime") val matchScoreHalftime: MatchGoalsHalf,
    @Embedded @SerializedName("fulltime") val matchScoreFulltime: MatchGoalsFull,
    @Embedded @SerializedName("extratime") val matchScoreExtratime: MatchGoalsExtra,
    @Embedded @SerializedName("penalty") val matchScorePenalty: MatchGoalsPenalties
)

data class MatchGoals(
    @SerializedName("home") var matchGoalsHome: Int?=null,
    @SerializedName("away") var matchGoalsAway: Int?=null
)

data class MatchGoalsHalf(
    @SerializedName("home") val matchGoalsHalfHome: Int?=null,
    @SerializedName("away") val matchGoalsHalfAway: Int?=null
)

data class MatchGoalsFull(
    @SerializedName("home") val matchGoalsFullHome: Int?=null,
    @SerializedName("away") val matchGoalsFullAway: Int?=null
)

data class MatchGoalsExtra(
    @SerializedName("home") val matchGoalsExtraHome: Int?=null,
    @SerializedName("away") val matchGoalsExtraAway: Int?=null
)

data class MatchGoalsPenalties(
    @SerializedName("home") val matchGoalsPenaltiesHome: Int?=null,
    @SerializedName("away") val matchGoalsPenaltiesAway: Int?=null
)


