package com.example.moviebase.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.MovieVideosFolder.Results
import com.example.moviebase.R
import kotlinx.android.synthetic.main.video_row.view.*

class MovieVideosAdapter(context: Context) : RecyclerView.Adapter<MovieVideosAdapter.MyViewHolder>() {

    private var videosList = emptyList<Results>()
    private var youtubeLink = "https://www.youtube.com/watch?v="
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video_row, parent, false)
        )
        return view
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = videosList[position]

        holder.itemView.videoName.text = currentItem.name

        holder.itemView.playButton.setOnClickListener {
            var youtubeID = currentItem.key
            val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
            val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
            try {
                holder.itemView.context.startActivity(intentApp)
            } catch (ex: ActivityNotFoundException) {
                holder.itemView.context.startActivity(intentBrowser)
            }
        }
    }

    fun setData(data: List<Results>) {
        this.videosList = data
        notifyDataSetChanged()
    }
}