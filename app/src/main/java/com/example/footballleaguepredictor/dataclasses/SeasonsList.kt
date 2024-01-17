package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class SeasonsList(@SerializedName("response") var seasonsList: List<Int>)
