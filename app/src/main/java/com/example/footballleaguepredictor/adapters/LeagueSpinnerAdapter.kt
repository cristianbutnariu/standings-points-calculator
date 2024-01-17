package com.example.footballleaguepredictor.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.dataclasses.Leagues
import com.squareup.picasso.Picasso

class LeagueSpinnerAdapter(context: Context, private var list: List<Leagues>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Leagues {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_league_selection_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        val uri: Uri = Uri.parse(list[position].league.leagueLogo)
        Picasso.get().load(uri).into(vh.flag)
        vh.name.text = list[position].league.leagueName

        return view
    }


    private class ItemHolder(row: View?) {
        val flag: ImageView
        val name: TextView

        init {
            flag = row?.findViewById(R.id.flagImageView) as ImageView
            name = row.findViewById(R.id.nameTv) as TextView
        }
    }

    fun setLeagues(list: List<Leagues>) {
        this.list = list
        notifyDataSetChanged()
    }

}