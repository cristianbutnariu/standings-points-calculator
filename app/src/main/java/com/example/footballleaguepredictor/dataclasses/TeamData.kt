package com.example.footballleaguepredictor.dataclasses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity("teams")
data class TeamData(
    @PrimaryKey(autoGenerate = true) var teamDataId: Int = 0,
    @Embedded @SerializedName("team") var teamInfo: TeamInfo,
    @Embedded @SerializedName("venue") var venue: VenueInfo,
    @Expose @SerializedName("id") var leagueId: Int=0,
    @Expose @SerializedName("year") var season: Int=0
)

data class TeamInfo(
    @SerializedName("id") var teamId: Int,
    @SerializedName("name") var teamName: String,
    @SerializedName("code") var teamCode: String?,
    @SerializedName("country") var teamCountry: String,
    @SerializedName("founded") var teamFounded: Int?,
    @SerializedName("national") var teamNational: Boolean,
    @SerializedName("logo") var teamLogo: String
)

data class VenueInfo(
    @SerializedName("id") var venueId: Int,
    @SerializedName("name") var venueName: String,
    @SerializedName("address") var venueAddress: String?,
    @SerializedName("city") var venueCity: String,
    @SerializedName("capacity") var venueCapacity: Int,
    @SerializedName("surface") var venueSurface: String,
    @SerializedName("image") var venueImage: String
)
