package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class TeamsList(@SerializedName("response") var teamsList: List<TeamData>)
