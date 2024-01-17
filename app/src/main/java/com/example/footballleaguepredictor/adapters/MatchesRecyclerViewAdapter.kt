package com.example.footballleaguepredictor.adapters

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.databinding.RvMatchFixtureLayoutBinding
import com.example.footballleaguepredictor.dataclasses.Match
import java.text.SimpleDateFormat
import java.util.Date

class MatchesRecyclerViewAdapter(private var results: List<Match>) :
    RecyclerView.Adapter<MatchesRecyclerViewAdapter.MatchesViewHolder>() {

    private var updatedResults: MutableList<Match> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvMatchFixtureLayoutBinding.inflate(inflater, parent, false)
        return MatchesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(
        holder: MatchesViewHolder,
        position: Int
    ) {
        var updatedMatch = results[position]
        var homeUpdate = false
        var awayUpdate = false
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        val date: Date? = results[position].matchFixture.fixtureDate?.let { sdf.parse(it) }
        val rvDate = date?.let { SimpleDateFormat("dd.MM yyyy").format(it) }
        holder.matchDate.text = rvDate
        results[position].matchTeams.teamHome.footballTeamHomeLogo?.let {
            holder.homeImage.loadSvg(
                it
            )
        }
        holder.homeName.text = results[position].matchTeams.teamHome.footballTeamHomeName

        holder.awayName.text = results[position].matchTeams.teamAway.footballTeamAwayName
        results[position].matchTeams.teamAway.footballTeamAwayLogo?.let {
            holder.awayImage.loadSvg(it)
        }

        holder.homeGoals.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    updatedMatch.matchGoals.matchGoalsHome = Integer.parseInt(p0.toString())
                    homeUpdate = true
                    if (homeUpdate && awayUpdate)
                        updatedResults.add(updatedMatch)
                }
            }
        })

        holder.awayGoals.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    updatedMatch.matchGoals.matchGoalsAway = Integer.parseInt(p0.toString())
                    awayUpdate = true
                    if (homeUpdate && awayUpdate)
                        updatedResults.add(updatedMatch)
                }
            }

        })
    }

    class MatchesViewHolder(binding: RvMatchFixtureLayoutBinding) : ViewHolder(binding.root) {
        val matchDate: TextView = binding.matchDate
        val homeImage: ImageView = binding.homeTeamPic
        val homeName: TextView = binding.homeTeamName
        val homeGoals: EditText = binding.homeTeamScore
        val awayGoals: EditText = binding.awayTeamScore
        val awayName: TextView = binding.awayTeamName
        val awayImage: ImageView = binding.awayTeamPic
    }

    private fun ImageView.loadSvg(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .placeholder(R.drawable.prediction_icon)
            .error(R.drawable.settings_icon)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMatches(list: List<Match>) {
        results = list
        notifyDataSetChanged()
    }

    fun getUpdatedResults(): List<Match> {
        return updatedResults
    }
}