package com.example.footballleaguepredictor.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("countries")
data class Country(
    @PrimaryKey(autoGenerate = true) var countryId: Int = 0,
    @SerializedName("name") var countryName: String? = "",
    @SerializedName("code") var countryCode: String? = "",
    @SerializedName("flag") var countryFlag: String? = ""
)
