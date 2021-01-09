package com.example.moviebase.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.TrendingModelFolder.Result
import com.example.moviebase.Fragments.MainFragmentDirections
import com.example.moviebase.R
import kotlinx.android.synthetic.main.trending_item.view.*


class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.MyViewHolder>() {
    private var trendingList = emptyList<Result>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_item, parent, false)

        view.setOnClickListener {
            //Toast.makeText(parent.context, view.trendingTitle.text, Toast.LENGTH_SHORT).show()
        }

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = trendingList[position]

        holder.itemView.trendingImage

        bindImage(holder.itemView.trendingImage, imageSource + currentItem.poster_path)
        holder.itemView.trendingTitle.text = currentItem.title

        var rating: Int = (currentItem.vote_average * 10).toInt()
        holder.itemView.movieRatingValue.text = "${rating}% "
        holder.itemView.movieRatingCircle.progress = rating

        holder.itemView.trendingMovie.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToMovieView(currentItem.id)
            holder.itemView.findNavController().navigate(action)
        }

        Log.d("komunikat", currentItem.id.toString())
    }

    fun setData(data: List<Result>) {
        this.trendingList = data
        notifyDataSetChanged()
    }

}