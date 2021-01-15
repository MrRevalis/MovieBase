package com.example.moviebase.Fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.example.moviebase.Adapter.bindImage
import com.example.moviebase.R
import com.example.moviebase.ViewModel.MovieViewModel
import com.example.moviebase.ViewModel.TV_ViewModel
import kotlinx.android.synthetic.main.fragment_movie_view.view.*
import java.lang.StringBuilder


class MovieView : Fragment() {

    private val args by navArgs<MovieViewArgs>()
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mTV_ViewModel: TV_ViewModel
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_view, container, false)

        mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        mTV_ViewModel = ViewModelProvider(this).get(TV_ViewModel::class.java)

        if (args.movieInfo.type == "movie") {
            mMovieViewModel.getMovieDetails(args.movieInfo.ID)
                .observe(viewLifecycleOwner, Observer { item ->
                    bindImage(view.backgroundImage, imageSource + item.backdrop_path)
                    bindImage(view.posterImage, imageSource + item.poster_path)
                    view.movieTitle.text = item.title
                    view.yearRelease.text = "(${item.release_date.split("-")[0]})"

                    var rating: Int = (item.vote_average * 10).toInt()

                    view.movieRating.progress = rating
                    view.movieRatingText.text = if (rating == 0) "NR" else "${rating}%"

                    view.dateRelease.text = item.release_date

                    var time = item.runtime
                    var minutes = time % 60
                    var hours = (time - (time % 60)) / 60
                    view.movieTime.text = "${hours}h ${minutes}m"

                    var movieType = ""
                    for (x in item.genres) {
                        movieType += "${x.name} "
                    }
                    view.movieType.text = movieType

                    view.movieDescription.text =
                        if (item.overview.isEmpty()) "Pusto" else item.overview

                    view.movieTagline.text = item.tagline
                })
        } else if (args.movieInfo.type == "tv") {
            mTV_ViewModel.getTVDetails(args.movieInfo.ID)
                .observe(viewLifecycleOwner, Observer { item ->
                    bindImage(view.backgroundImage, imageSource + item.backdrop_path)
                    bindImage(view.posterImage, imageSource + item.poster_path)
                    view.movieTitle.text = item.name
                    view.yearRelease.text = "(${item.first_air_date.split("-")[0]})"

                    var rating: Int = (item.vote_average * 10).toInt()

                    view.movieRating.progress = rating
                    view.movieRatingText.text = if (rating == 0) "NR" else "${rating}%"

                    view.dateRelease.text = item.first_air_date

                    var time = item.episode_run_time
                    if (time.size == 3)
                        view.movieTime.text = "${time[0]}h ${time[1]}m ${time[2]}s"
                    else if (time.size == 2)
                        view.movieTime.text = "${time[0]}m ${time[1]}s"
                    else if (time.size == 1)
                        view.movieTime.text = "${time[0]}m"
                    else{
                        view.movieTime.text = "Pusto"
                    }


                    var movieType : StringBuilder = StringBuilder()
                    for (x in item.genres) {
                        movieType.append("${x.name} ")
                    }
                    Log.d("komunikat", movieType.toString())
                    view.movieType.text = movieType.toString()

                    view.movieDescription.text =
                        if (item.overview.isNullOrEmpty()) "Pusto" else item.overview

                    view.movieTagline.text = item.tagline
                })
        }


        return view
    }

    companion object {
        fun newInstance() = MovieView()
    }
}