package com.example.moviebase.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.MovieVideosFolder.Results
import com.example.moviebase.MainActivity
import com.example.moviebase.R
import com.example.moviebase.YouTubePlayerActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.video_row.view.*

const val APIYT = "AIzaSyALH-jVfLYM34GdrBcnkPMSNIZzRlpJa3Y"
class MovieVideosAdapter() : RecyclerView.Adapter<MovieVideosAdapter.MyViewHolder>() {

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

        /*holder.itemView.playButton.setOnClickListener {
            var youtubeID = currentItem.key
            val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
            val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
            try {
                holder.itemView.context.startActivity(intentApp)
            } catch (ex: ActivityNotFoundException) {
                holder.itemView.context.startActivity(intentBrowser)
            }
        }*/

        holder.itemView.playVideo.setOnClickListener {it->
            //it.context.startActivity(Intent(it.context, MainActivity::class.java).putExtra("video_id",currentItem.key))
            it.context.startActivity(Intent(it.context, YouTubePlayerActivity::class.java).putExtra("video_id",currentItem.key))
        }

        holder.itemView.youtubeVideo.initialize(APIYT, object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubeThumbnailView?,
                youTubeThumbnailLoader: YouTubeThumbnailLoader?
            ) {
                youTubeThumbnailLoader!!.setVideo(currentItem.key)

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener{
                    override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                        youTubeThumbnailLoader.release()
                    }

                    override fun onThumbnailError(
                        p0: YouTubeThumbnailView?,
                        p1: YouTubeThumbnailLoader.ErrorReason?
                    ) {
                        Log.d("komunikat", "Blad wczytywania=> ${p1.toString()}")
                    }
                })
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("komunikat", "Blad inicjalizacji => ${p1.toString()}")
            }
        })

    }

    fun setData(data: List<Results>) {
        this.videosList = data
        notifyDataSetChanged()
    }

}