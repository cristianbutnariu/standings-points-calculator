package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class MatchList(@SerializedName("response") var matchList: List<Match>)
