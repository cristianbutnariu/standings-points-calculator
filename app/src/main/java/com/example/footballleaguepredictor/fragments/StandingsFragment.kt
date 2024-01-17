package com.example.footballleaguepredictor.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.footballleaguepredictor.adapters.StandingsRecyclerViewAdapter
import com.example.footballleaguepredictor.databinding.FragmentStandingsBinding
import com.example.footballleaguepredictor.dataclasses.Team
import com.example.footballleaguepredictor.viewmodels.DataViewModel

class StandingsFragment : Fragment(), StandingsRecyclerViewAdapter.TeamActionListener {
    private lateinit var binding: FragmentStandingsBinding
    private lateinit var standingsRecyclerView: RecyclerView
    private lateinit var layoutManager: LayoutManager
    private lateinit var standingsAdapter: StandingsRecyclerViewAdapter

    private lateinit var mainViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.getStandings(2, 2023)
        //960 euro qualifiers
        //2 ucl

        setUpObservers()

        standingsRecyclerView = binding.standingsRv
        layoutManager = LinearLayoutManager(context)

        standingsRecyclerView.layoutManager = layoutManager

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]

        return binding.root
    }

    private fun setUpObservers() {
        mainViewModel.standingsLiveData.observe(viewLifecycleOwner) { standings ->
            val list=standings[0].leagueStandingsLeague.leagueRankingStandings
            Log.d("standingsLiveData", list?.size.toString())
            Log.d("standingsLiveData", list.toString())


            if (list != null) {
                standingsAdapter = StandingsRecyclerViewAdapter(list, this)
                standingsRecyclerView.adapter=standingsAdapter
               // standingsAdapter.setStandings(list)
            }
        }
    }

    override fun teamDetails(team: Team, position: Int) {
        Log.d("rvPosition", position.toString())
    }

}