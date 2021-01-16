package com.example.moviebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.Model.Database.Favourite
import com.example.moviebase.R
import kotlinx.android.synthetic.main.favourite_row.view.*

class FavouriteAdapter() : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    private var favouriteList = emptyList<Favourite>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favourite_row, parent, false)
        )

        return view
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = favouriteList[position]
        bindImage(holder.itemView.favouritePoster, imageSource + currentItem.imgPath)
        holder.itemView.favouriteTitle.text = currentItem.title
        holder.itemView.favouriteDescription.text = currentItem.description
    }

    fun setData(data: List<Favourite>) {
        this.favouriteList = data
        notifyDataSetChanged()
    }
}

