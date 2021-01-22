package com.example.moviebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.HelperClass
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularContent
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularMovieResults
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularTVResults
import com.example.moviebase.Fragments.MainFragmentDirections
import com.example.moviebase.R
import kotlinx.android.synthetic.main.trending_item.view.*

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.MyViewHolder>() {
    private var popularList = emptyList<PopularContent>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.trending_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = popularList[position]

        holder.itemView.trendingImage
        bindImage(holder.itemView.trendingImage, imageSource + currentItem.poster_path)
        holder.itemView.trendingTitle.text = if (currentItem.title.isNullOrEmpty()) currentItem.name else currentItem.title
        holder.itemView.trendingRelease.text = if(currentItem.release_date.isNullOrEmpty()) currentItem.first_air_date else currentItem.release_date
        var rating: Int = (currentItem.vote_average * 10).toInt()
        holder.itemView.movieRatingText.text = if (rating == 0) "NR" else "${rating}%"
        holder.itemView.movieRating.progress = rating

        holder.itemView.trendingMovie.setOnClickListener {
            var title = if (currentItem.title.isNullOrEmpty()) "" else currentItem.title
            val action = MainFragmentDirections.actionMainFragmentToMovieView(
                HelperClass(
                    currentItem.id,
                    currentItem.type
                ), title
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<PopularContent>) {
        this.popularList = data
        notifyDataSetChanged()
    }

}