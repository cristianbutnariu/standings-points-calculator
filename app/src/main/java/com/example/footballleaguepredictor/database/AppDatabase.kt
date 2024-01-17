package com.example.footballleaguepredictor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData

@Database(
    entities = [Country::class, Leagues::class, LeagueStandings::class, Match::class, Seasons::class, TeamData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): DatabaseDAO

    companion object {
        private const val DB_NAME = "database"

        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).setJournalMode(JournalMode.WRITE_AHEAD_LOGGING).fallbackToDestructiveMigration()
                .build()
        }

    }

}