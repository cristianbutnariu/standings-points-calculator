package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class LeaguesList(@SerializedName("response") var leaguesList: List<Leagues>)
