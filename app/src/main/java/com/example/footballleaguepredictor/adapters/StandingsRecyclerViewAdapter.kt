package com.example.footballleaguepredictor.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.databinding.RvHeaderBinding
import com.example.footballleaguepredictor.databinding.RvStandingsLayoutBinding
import com.example.footballleaguepredictor.dataclasses.FootballGroup
import com.example.footballleaguepredictor.dataclasses.Team

class StandingsRecyclerViewAdapter(
    private var standingsList: List<List<FootballGroup>>,
    private val teamActionListener: TeamActionListener
) :
    RecyclerView.Adapter<ViewHolder>() {
    private var headers = 0
    private var groupIndex = 0
    private var teamIndex = 0
    private var lastPosition = 0

    companion object {
        private const val GROUP = 0
        private const val HEADER = 1
    }

    interface TeamActionListener {
        fun teamDetails(team: Team, position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val holder: ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        holder = when (viewType) {
            GROUP -> {
                val standingsLayoutBinding =
                    RvStandingsLayoutBinding.inflate(inflater, parent, false)
                StandingsViewHolder(standingsLayoutBinding)
            }

            else -> {
                val headerLayoutBinding =
                    RvHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(headerLayoutBinding)

            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        when (getItemViewType(position)) {
            GROUP -> {
                val svh = holder as StandingsViewHolder
                configureStandings(svh, position)
            }

            else -> {
                val hvh = holder as HeaderViewHolder
                configureHeader(hvh, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return getArraySize() + standingsList.size
    }

    private fun getArraySize(): Int {
        var noElements = 0
        for (element in standingsList)
            noElements += element.size
        return noElements
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % (standingsList[groupIndex].size + 1) == 0) {
            HEADER
        } else
            GROUP
    }

    class StandingsViewHolder(binding: RvStandingsLayoutBinding) : ViewHolder(binding.root) {
        val teamRank = binding.place
        val teamLogo = binding.teamPic
        val teamName = binding.teamName
        val teamMatches = binding.matchesPlayed
        val teamGoalsDifference = binding.goalsDifference
        val teamPoints = binding.points
        val teamForm = binding.form

    }

    class HeaderViewHolder(binding: RvHeaderBinding) : ViewHolder(binding.root) {
        val standingsTitle = binding.textView
    }

    private fun configureStandings(svh: StandingsViewHolder, position: Int) {
        if (position == 0) {
            headers = 1
        } else if (position % (standingsList[groupIndex].size + 1) != 0)
            headers = (position / (standingsList[groupIndex].size + 1)) + 1

        groupIndex = (position - headers) / standingsList[groupIndex].size
        teamIndex =
            ((position - headers) - (position - headers) / standingsList[groupIndex].size * standingsList[(position - headers) / standingsList[groupIndex].size].size)
        Log.d(
            "indicesCheck",
            "Position: $position \nHeaders: $headers \nGroup: $groupIndex \nTeam: $teamIndex"
        )
        svh.teamRank.text =
            standingsList[groupIndex][teamIndex].footballGroupRank.toString()
        if (standingsList[groupIndex][teamIndex].footballGroupDescription.isNullOrBlank()) {
            svh.teamRank.setBackgroundColor(
                ContextCompat.getColor(
                    svh.teamRank.context,
                    android.R.color.transparent
                )
            )
            svh.teamRank.setTextColor(Color.BLACK)
        } else if (standingsList[groupIndex][teamIndex].footballGroupDescription!!.contains(
                "champion",
                ignoreCase = true
            )
        ) {
            svh.teamRank.setBackgroundColor(
                ContextCompat.getColor(
                    svh.teamRank.context,
                    R.color.ucl_blue
                )
            )
            svh.teamRank.setTextColor(Color.WHITE)
        } else if (standingsList[groupIndex][teamIndex].footballGroupDescription!!.contains(
                "conference",
                ignoreCase = true
            )
        ) {
            svh.teamRank.setBackgroundColor(
                ContextCompat.getColor(
                    svh.teamRank.context,
                    R.color.uecl_green
                )
            )
            svh.teamRank.setTextColor(Color.WHITE)
        } else if (standingsList[groupIndex][teamIndex].footballGroupDescription!!.contains(
                "europa",
                ignoreCase = true
            )
        ) {
            svh.teamRank.setBackgroundColor(
                ContextCompat.getColor(
                    svh.teamRank.context,
                    R.color.uel_red
                )
            )
            svh.teamRank.setTextColor(Color.WHITE)
        } else if (standingsList[groupIndex][teamIndex].footballGroupDescription!!.contains(
                "relegation",
                ignoreCase = true
            )
        ) {
            svh.teamRank.setBackgroundColor(
                ContextCompat.getColor(
                    svh.teamRank.context,
                    R.color.red
                )
            )
            svh.teamRank.setTextColor(Color.WHITE)
        }


        svh.teamLogo.loadSvg(standingsList[groupIndex][teamIndex].footballGroupTeam.teamLogo)
        svh.teamName.text =
            standingsList[groupIndex][teamIndex].footballGroupTeam.teamName
        svh.teamMatches.text =
            standingsList[groupIndex][teamIndex].footballGroupAll.matchRecordPlayed.toString()
        svh.teamGoalsDifference.text =
            standingsList[groupIndex][teamIndex].footballGroupGoalsDiff.toString()
        svh.teamPoints.text =
            standingsList[groupIndex][teamIndex].footballGroupPoints.toString()
        val form =
            standingsList[groupIndex][teamIndex].footballGroupForm
        val formLayout = svh.teamForm.root
        for (i in 4 downTo 1) {
            val tv = formLayout.getChildAt(i - 1) as TextView
            when (form[i - 1]) {
                'W' -> {
                    tv.setBackgroundColor(
                        ContextCompat.getColor(
                            svh.teamForm.root.context,
                            R.color.green
                        )
                    )
                    tv.text = "W"
                }

                'D' -> {
                    tv.setBackgroundColor(
                        ContextCompat.getColor(
                            svh.teamForm.root.context,
                            R.color.grey
                        )
                    )
                    tv.text = "D"
                }

                'L' -> {
                    tv.setBackgroundColor(
                        ContextCompat.getColor(
                            svh.teamForm.root.context,
                            R.color.red
                        )
                    )
                    tv.text = "L"
                }
            }
        }

        svh.itemView.setOnClickListener {
            teamActionListener.teamDetails(
                standingsList[groupIndex][teamIndex].footballGroupTeam,
                position
            )
        }

    }

    private fun configureHeader(hvh: HeaderViewHolder, position: Int) {
        if (position == 0) {
            headers = 1
        } else if (position % (standingsList[groupIndex].size + 1) == 0)
            headers = (position / (standingsList[groupIndex].size + 1)) + 1
        val groupIndex = ((position - headers+1) / standingsList[groupIndex].size)
        Log.d("groupIndexCheck", groupIndex.toString())
        val teamIndex =
            ((position) - (position) / standingsList[groupIndex].size * standingsList[(position - headers) / standingsList[groupIndex].size].size)
        Log.d(
            "headerCheck",
            "Position: $position \nHeaders: $headers \nGroup: $groupIndex \nTeam: $teamIndex"
        )
        hvh.standingsTitle.text =
            standingsList[groupIndex][teamIndex].footballGroupGroup
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStandings(list: List<List<FootballGroup>>) {
        standingsList = list
        notifyDataSetChanged()
        Log.d("adapterCheck", standingsList.toString())
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
}