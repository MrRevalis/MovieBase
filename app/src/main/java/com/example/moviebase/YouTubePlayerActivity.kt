package com.example.moviebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.moviebase.Adapter.APIYT
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class YouTubePlayerActivity : YouTubeBaseActivity() {

    private lateinit var videoID: String
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_tube_player)

        videoID = intent.getStringExtra("video_id")?: ""
        youTubePlayerView = findViewById(R.id.youtubePlayer)
        InitializeYouTubePlayer()
    }

    private fun InitializeYouTubePlayer(){
        youTubePlayerView.initialize(APIYT, object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if(!p2){
                    Log.d("komunikat", "jest cool")
                    p1!!.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    p1!!.loadVideo(videoID)
                    //p1!!.setFullscreen(true)
                    p1!!.play()
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("komunikat", p1.toString())
            }

        })
    }
}