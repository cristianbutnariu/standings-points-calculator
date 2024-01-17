package com.example.footballleaguepredictor.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.adapters.CountriesSpinnerAdapter
import com.example.footballleaguepredictor.adapters.LeagueSpinnerAdapter
import com.example.footballleaguepredictor.adapters.TeamsSpinnerAdapter
import com.example.footballleaguepredictor.database.AppDatabase
import com.example.footballleaguepredictor.databinding.ActivityMainBinding
import com.example.footballleaguepredictor.dataclasses.Country
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.example.footballleaguepredictor.viewmodels.DataViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var countriesAdapter: CountriesSpinnerAdapter
    private lateinit var leagueAdapter: LeagueSpinnerAdapter
    private lateinit var teamsAdapter: TeamsSpinnerAdapter


    private val viewModel: DataViewModel by viewModels { DataViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.roomDao()

        viewModel.getCountries()
        viewModel.getSeasons()

        countriesAdapter = CountriesSpinnerAdapter(applicationContext, arrayListOf())
        binding.countriesSpinner.adapter = countriesAdapter

        leagueAdapter = LeagueSpinnerAdapter(applicationContext, arrayListOf())
        binding.leaguesSpinner.adapter = leagueAdapter

        teamsAdapter = TeamsSpinnerAdapter(applicationContext, arrayListOf())
        binding.teamSpinner.adapter = teamsAdapter

        setUpObservers()

        binding.countriesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    val country = parent.getItemAtPosition(position) as Country
                    country.countryName?.let { viewModel.getLeague(it) }
                    ((parent.getChildAt(0) as ConstraintLayout).getChildAt(1) as TextView).setTextColor(
                        Color.parseColor("#FFFFFF")
                    )
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.seasonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null && binding.leaguesSpinner.selectedItem != null) {
                    val league = binding.leaguesSpinner.selectedItem as Leagues
                    val season = parent.selectedItem as Int
                    viewModel.getTeams(league.league.leagueId, season)
                    (parent.getChildAt(0) as TextView).setTextColor(
                        Color.parseColor("#FFFFFF")
                    )
                    (parent.getChildAt(0) as TextView).typeface = Typeface.DEFAULT_BOLD


                } else if (parent != null) {
                    (parent.getChildAt(0) as TextView).setTextColor(
                        Color.parseColor("#FFFFFF")
                    )
                    (parent.getChildAt(0) as TextView).typeface = Typeface.DEFAULT_BOLD

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.leaguesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    val league = parent.getItemAtPosition(position) as Leagues
                    val season = binding.seasonSpinner.selectedItem as Int
                    viewModel.getTeams(league.league.leagueId, season)
                    ((parent.getChildAt(0) as ConstraintLayout).getChildAt(1) as TextView).setTextColor(
                        Color.parseColor("#FFFFFF")
                    )
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.teamSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    ((parent.getChildAt(0) as ConstraintLayout).getChildAt(1) as TextView).setTextColor(
                        Color.parseColor("#FFFFFF")
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        navView = binding.bottomNavBar

        binding.bottomNavBar.setupWithNavController(navController)

    }

    private fun setUpObservers() {
        viewModel.seasonsLiveData.observe(this) { seasons ->
            val seasonsList = seasons.sortedByDescending { it.season }.map { it.season }
            val seasonsAdapter = applicationContext?.let {
                ArrayAdapter(
                    it, android.R.layout.simple_spinner_item,
                    seasonsList
                )
            }
            binding.seasonSpinner.adapter = seasonsAdapter
        }

        viewModel.countriesLiveData.observe(this) { countries ->
            countriesAdapter.setCountries(countries)
            viewModel.getLeague(countries[0].countryName!!)
        }

        viewModel.leagueLiveData.observe(this) { league ->
            leagueAdapter.setLeagues(league)
            viewModel.getTeams(league[0].league.leagueId, binding.seasonSpinner.selectedItem as Int)

        }

        viewModel.teamsLiveData.observe(this) { teams ->
            teamsAdapter.setTeams(teams)
            viewModel.getFixtures(
                (binding.leaguesSpinner.selectedItem as Leagues).league.leagueId,
                binding.seasonSpinner.selectedItem as Int
            )
            viewModel.getStandings(
                (binding.leaguesSpinner.selectedItem as Leagues).league.leagueId,
                binding.seasonSpinner.selectedItem as Int
            )
        }

    }
}