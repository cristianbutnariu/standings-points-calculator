package com.example.footballleaguepredictor.database

import androidx.room.TypeConverter
import com.example.footballleaguepredictor.dataclasses.FootballGroup
import com.example.footballleaguepredictor.dataclasses.Season
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromSeasonListToJSON(sl: List<Season>?): String = Gson().toJson(sl)

    @TypeConverter
    fun fromJSONToSeasonList(json: String): List<Season>? =
        Gson().fromJson(json, object :
            TypeToken<List<Season?>?>() {}.type)

    @TypeConverter
    fun fromFootballGroupListListToJSON(sl: List<List<FootballGroup>>?): String = Gson().toJson(sl)

    @TypeConverter
    fun fromJSONToFootballGroupListList(json: String): List<List<FootballGroup>>? =
        Gson().fromJson(json, object :
            TypeToken<List<List<FootballGroup?>?>?>() {}.type)


}