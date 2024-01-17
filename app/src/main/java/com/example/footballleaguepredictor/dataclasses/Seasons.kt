package com.example.footballleaguepredictor.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("seasons")
data class Seasons(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("seasonId") var id: Int = 0,
    @SerializedName("season") var season: Int
)
