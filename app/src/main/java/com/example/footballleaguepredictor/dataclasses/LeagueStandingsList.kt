package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class LeagueStandingsList(@SerializedName("response") var leaguesStandingsList: List<LeagueStandings>)
