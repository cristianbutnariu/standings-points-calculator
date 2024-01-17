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
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.adapters.MatchesRecyclerViewAdapter
import com.example.footballleaguepredictor.adapters.ResultsRecyclerViewAdapter
import com.example.footballleaguepredictor.databinding.FragmentPredictionBinding
import com.example.footballleaguepredictor.viewmodels.DataViewModel

class PredictionFragment : Fragment() {
    private lateinit var binding: FragmentPredictionBinding

    private lateinit var recyclerViewResults: RecyclerView
    private lateinit var recyclerViewMatches: RecyclerView
    private lateinit var resultsLayoutManager: LinearLayoutManager
    private lateinit var matchesLayoutManager: LinearLayoutManager

    private lateinit var resultsAdapter: ResultsRecyclerViewAdapter
    private lateinit var fixturesAdapter: MatchesRecyclerViewAdapter

    private lateinit var mainViewModel: DataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListeners()

        recyclerViewResults = binding.resultsLayout.recyclerView
        recyclerViewMatches = binding.matchesLayout.recyclerView
        binding.matchesLayout.questionTitle.text = getString(R.string.matches)
        resultsLayoutManager = LinearLayoutManager(context)
        matchesLayoutManager = LinearLayoutManager(context)


        resultsAdapter = ResultsRecyclerViewAdapter(arrayListOf())
        fixturesAdapter = MatchesRecyclerViewAdapter(arrayListOf())

        recyclerViewResults.layoutManager = resultsLayoutManager
        recyclerViewResults.adapter = resultsAdapter

        recyclerViewMatches.layoutManager = matchesLayoutManager
        recyclerViewMatches.adapter = fixturesAdapter

        setupObservers()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPredictionBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]
        return binding.root
    }


    private fun setupOnClickListeners() {
        binding.resultsLayout.root.setOnClickListener {
            if (binding.resultsLayout.fourAnswersCheckbox.visibility == View.VISIBLE)
                binding.resultsLayout.fourAnswersCheckbox.visibility = View.GONE
            else
                binding.resultsLayout.fourAnswersCheckbox.visibility = View.VISIBLE
        }

        binding.matchesLayout.root.setOnClickListener {
            if (binding.matchesLayout.fourAnswersCheckbox.visibility == View.VISIBLE)
                binding.matchesLayout.fourAnswersCheckbox.visibility = View.GONE
            else
                binding.matchesLayout.fourAnswersCheckbox.visibility = View.VISIBLE
        }

        binding.resultsLayout.expandArrowImageView.setOnClickListener {
            if (binding.resultsLayout.fourAnswersCheckbox.visibility == View.VISIBLE)
                binding.resultsLayout.fourAnswersCheckbox.visibility = View.GONE
            else
                binding.resultsLayout.fourAnswersCheckbox.visibility = View.VISIBLE
        }

        binding.matchesLayout.expandArrowImageView.setOnClickListener {
            if (binding.matchesLayout.fourAnswersCheckbox.visibility == View.VISIBLE)
                binding.matchesLayout.fourAnswersCheckbox.visibility = View.GONE
            else
                binding.matchesLayout.fourAnswersCheckbox.visibility = View.VISIBLE
        }
    }

    private fun setupObservers() {
        mainViewModel.fixturesLiveData.observe(viewLifecycleOwner) { matches ->
            val fixtures =
                matches.filter { it.matchFixture.fixtureStatus.matchStatusShort == "NS" || it.matchFixture.fixtureStatus.matchStatusShort == "TBD" }
                    .sortedBy { it.matchFixture.fixtureDate }
            val results =
                matches.filterNot { it.matchFixture.fixtureStatus.matchStatusShort == "NS" || it.matchFixture.fixtureStatus.matchStatusShort == "TBD" }
                    .reversed().sortedByDescending { it.matchFixture.fixtureDate }
            resultsAdapter.setResults(results)
            fixturesAdapter.setMatches(fixtures)
            Log.d("fixturesCheck", fixtures.toString())
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", fixturesAdapter.getUpdatedResults().toString())
    }


}