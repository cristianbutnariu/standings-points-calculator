package com.example.footballleaguepredictor.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.databinding.RvMatchResultLayoutBinding
import com.example.footballleaguepredictor.dataclasses.Match
import java.text.SimpleDateFormat
import java.util.Date

class ResultsRecyclerViewAdapter(private var results: List<Match>) :
    RecyclerView.Adapter<ResultsRecyclerViewAdapter.FixturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvMatchResultLayoutBinding.inflate(inflater, parent, false)
        return FixturesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
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
            holder.homeGoals.text = results[position].matchGoals.matchGoalsHome.toString()
            holder.awayGoals.text = results[position].matchGoals.matchGoalsAway.toString()
            holder.awayName.text = results[position].matchTeams.teamAway.footballTeamAwayName
            results[position].matchTeams.teamAway.footballTeamAwayLogo?.let {
                holder.awayImage.loadSvg(
                    it
                )
            }
            if (results[position].matchGoals.matchGoalsHome!! > results[position].matchGoals.matchGoalsAway!!) {
                holder.homeName.setTypeface(null, Typeface.BOLD)
                holder.awayName.setTypeface(null, Typeface.NORMAL)
            } else if (results[position].matchGoals.matchGoalsHome!! < results[position].matchGoals.matchGoalsAway!!) {
                holder.awayName.setTypeface(null, Typeface.BOLD)
                holder.homeName.setTypeface(null, Typeface.NORMAL)
            } else if (results[position].matchGoals.matchGoalsHome!! == results[position].matchGoals.matchGoalsAway!!) {
                holder.homeName.setTypeface(null, Typeface.NORMAL)
                holder.awayName.setTypeface(null, Typeface.NORMAL)

            }
    }

    class FixturesViewHolder(binding: RvMatchResultLayoutBinding) : ViewHolder(binding.root) {
        val matchDate: TextView = binding.matchDate
        val homeImage: ImageView = binding.homeTeamPic
        val homeName: TextView = binding.homeTeamName
        val homeGoals: TextView = binding.homeTeamScore
        val awayGoals: TextView = binding.awayTeamScore
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
    fun setResults(list: List<Match>) {
        results = list
        notifyDataSetChanged()
    }

}