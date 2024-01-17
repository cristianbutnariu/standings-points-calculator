package com.example.footballleaguepredictor.dataclasses

import com.google.gson.annotations.SerializedName

data class CountriesList(@SerializedName("response") var countriesList: List<Country>)