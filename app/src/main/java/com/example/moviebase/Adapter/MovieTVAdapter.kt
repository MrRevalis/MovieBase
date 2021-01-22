package com.example.moviebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.HelperClass
import com.example.moviebase.DataModels.PersonMoviesTVsFolder.PersonMovieTVCast
import com.example.moviebase.Fragments.PersonFragmentDirections
import com.example.moviebase.R
import kotlinx.android.synthetic.main.movie_tv_row.view.*

class MovieTVAdapter : RecyclerView.Adapter<MovieTVAdapter.MyViewHolder>() {
    private var movieTVList = emptyList<PersonMovieTVCast>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_tv_row, parent, false)
        )
        return view
    }

    override fun getItemCount(): Int {
        return movieTVList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = movieTVList[position]
        bindImage(holder.itemView.movieTvPoster, imageSource + currentItem.poster_path)
        holder.itemView.originalTitle.text = currentItem.original_title
        holder.itemView.title.text = currentItem.title

        holder.itemView.setOnClickListener {
            val action = PersonFragmentDirections.actionPersonFragmentToMovieView(
                HelperClass(currentItem.id, currentItem.media_type), currentItem.title
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<PersonMovieTVCast>){
        this.movieTVList = data
        notifyDataSetChanged()
    }
}