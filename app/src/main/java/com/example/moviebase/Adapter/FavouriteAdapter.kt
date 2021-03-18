package com.example.moviebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.HelperClass
import com.example.moviebase.Fragments.FavouriteListDirections
import com.example.moviebase.Model.Database.Show
import com.example.moviebase.R
import kotlinx.android.synthetic.main.favourite_row.view.*

class FavouriteAdapter() : RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {

    private var favouriteList = emptyList<Show>()
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
        bindImage(holder.itemView.favouritePoster, imageSource + currentItem.posterPath)
        holder.itemView.favouriteTitle.text = currentItem.title
        holder.itemView.favouriteDescription.text = currentItem.movieDescription

        /*if(currentItem.toWatch){
            holder.itemView.labelIcon.setImageResource(R.drawable.ic_eye_true)
        } else holder.itemView.labelIcon.visibility = View.GONE*/
        holder.itemView.labelIcon.visibility = View.GONE //egzamin zad. 1

        holder.itemView.setOnClickListener {
            var helperClass = HelperClass(currentItem.itemID, currentItem.type)
            val action = FavouriteListDirections.actionFavouriteListToMovieView(helperClass, currentItem.title)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<Show>) {
        this.favouriteList = data
        notifyDataSetChanged()
    }
}

