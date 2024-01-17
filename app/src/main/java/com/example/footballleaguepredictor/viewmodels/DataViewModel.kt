package com.example.footballleaguepredictor.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.footballleaguepredictor.database.AppDatabase
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.LeagueStandings
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.dataclasses.Match
import com.example.footballleaguepredictor.dataclasses.Seasons
import com.example.footballleaguepredictor.dataclasses.TeamData
import com.example.footballleaguepredictor.retrofit.APIClient
import com.example.footballleaguepredictor.retrofit.DataSourceImplementation
import com.example.footballleaguepredictor.retrofit.LocalDataSourceImplementation
import com.example.footballleaguepredictor.retrofit.Repository
import kotlinx.coroutines.launch

class DataViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(
        application
    ) {

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
                    val repository = Repository(
                        DataSourceImplementation(APIClient.getInstance()),
                        LocalDataSourceImplementation(
                            AppDatabase.getInstance(application.applicationContext)
                        ), application
                    )
                    return DataViewModel(repository, application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val sharedPref = application.getSharedPreferences(
        "apiCallTiming",
        Context.MODE_PRIVATE
    )

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: MutableLiveData<String> = _errorLiveData

    private var _connectionStatus = MutableLiveData<String>()
    val connectionStatus: MutableLiveData<String> = _connectionStatus

    private var _countriesLiveData = MutableLiveData<List<Country>>()
    val countriesLiveData: MutableLiveData<List<Country>> = _countriesLiveData

    private var _leagueLiveData = MutableLiveData<List<Leagues>>()
    val leagueLiveData: MutableLiveData<List<Leagues>> = _leagueLiveData

    private var _standingsLiveData = MutableLiveData<List<LeagueStandings>>()
    val standingsLiveData: MutableLiveData<List<LeagueStandings>> = _standingsLiveData

    private var _fixturesLiveData = MutableLiveData<List<Match>>()
    val fixturesLiveData: MutableLiveData<List<Match>> = _fixturesLiveData

    private var _seasonsLiveData = MutableLiveData<List<Seasons>>()
    val seasonsLiveData: MutableLiveData<List<Seasons>> = _seasonsLiveData

    private var _teamsLiveData = MutableLiveData<List<TeamData>>()
    val teamsLiveData: MutableLiveData<List<TeamData>> = _teamsLiveData

    private val _updatedMatches = MutableLiveData<List<Match>>()
    val updatedMatches: MutableLiveData<List<Match>> = _updatedMatches

    fun getStatus() {
        viewModelScope.launch {
            val response = repository.getAccountStatus()
            if (response.isSuccessful)
                _connectionStatus.value = response.body()
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            try {
                val response = repository.getCountries()
                if (sharedPref.getBoolean("countriesSuccess", false))
                    _countriesLiveData.value = response
                else
                    _errorLiveData.value = "Failed to retrieve the countries"
            } catch (e: Exception) {
                Log.d("getCountriesViewModel", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun getLeague(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLeague(name)
                Log.d("getLeagueViewModelCheck", response.toString())
                if (sharedPref.getBoolean("leaguesSuccess", false))
                    _leagueLiveData.value = response
                else
                    _errorLiveData.value = "Failed to retrieve the league"
            } catch (e: Exception) {
                Log.d("getLeagueCheck", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun getStandings(id: Int, year: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getStandings(id, year)
                if (sharedPref.getBoolean("standingsSuccess${id + year}", false))
                    _standingsLiveData.value = response
                else
                    _errorLiveData.value = "Failed to retrieve the standings"
                Log.d("getStandingsViewModel", response.toString())
            } catch (e: Exception) {
                Log.d("getStandingsViewModel", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun getFixtures(id: Int, year: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getFixtures(id, year)
                if (sharedPref.getBoolean("fixturesSuccess${id + year}", false))
                    _fixturesLiveData.value = response
                else
                    _errorLiveData.value = "Failed to retrieve the fixtures"
                Log.d("getFixturesViewModel", response.size.toString())
            } catch (e: Exception) {
                Log.d("getFixturesViewModel", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun getSeasons() {
        viewModelScope.launch {
            try {
                val response = repository.getSeasons()
                if (sharedPref.getBoolean("seasonsSuccess", false))
                    _seasonsLiveData.value = response
                else
                    _errorLiveData.value = "Failed to retrieve the seasons"
            } catch (e: Exception) {
                Log.d("getSeasonsViewModel", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun getTeams(id: Int, year: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTeams(id, year)
                _teamsLiveData.value = response
            } catch (e: Exception) {
                Log.d("getTeamsViewModel", e.toString())
                _errorLiveData.value = e.toString()
            }
        }
    }

    fun setUpdatedMatches(list: List<Match>) {
        viewModelScope.launch {
            _updatedMatches.value = list
        }
    }

}