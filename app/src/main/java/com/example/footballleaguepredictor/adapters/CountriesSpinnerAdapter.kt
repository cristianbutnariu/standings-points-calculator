package com.example.footballleaguepredictor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.footballleaguepredictor.R
import com.example.footballleaguepredictor.dataclasses.Country

class CountriesSpinnerAdapter(private var context: Context, private var list: List<Country>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Country {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_country_selection_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        if (list[position].countryFlag.isNullOrEmpty())
            vh.flag.setImageResource(R.drawable.eu_flag)
        else
            list[position].countryFlag?.let { vh.flag.loadSvg(it) }
        vh.name.text = list[position].countryName

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

    fun setCountries(list: List<Country>) {
        this.list = list
        notifyDataSetChanged()
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