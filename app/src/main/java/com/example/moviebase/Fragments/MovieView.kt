package com.example.moviebase.Fragments

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviebase.Adapter.bindImage
import com.example.moviebase.Adapter.setPictureWithBackground
import com.example.moviebase.DataModels.MovieViewClass
import com.example.moviebase.Model.Database.Favourite
import com.example.moviebase.R
import com.example.moviebase.ViewModel.FavouriteViewModel
import com.example.moviebase.ViewModel.MovieViewModel
import com.example.moviebase.ViewModel.TV_ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_movie_view.*
import kotlinx.android.synthetic.main.fragment_movie_view.view.*
import kotlinx.android.synthetic.main.search_result_row.*

const val imageSource: String = "https://image.tmdb.org/t/p/w500"

class MovieView : Fragment() {

    private val args by navArgs<MovieViewArgs>()
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mTV_ViewModel: TV_ViewModel
    private lateinit var mFavouriteViewModel: FavouriteViewModel
    private lateinit var movieClass: MovieViewClass
    var buttonContainerClicked : Boolean  = false;

    private lateinit var showButtonAnimation : Animation
    private lateinit var hideButtonAnimation : Animation
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
        mFavouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        showButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.show_animation)
        hideButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.hide_animation)

        checkIfIsFavourite(args.movieInfo.ID)
        var movieType = args.movieInfo.type

        when (movieType) {
            "movie" -> {
                mMovieViewModel.getMovieDetails(args.movieInfo.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        movieClass = MovieViewClass(item)
                        calculateRating(item.vote_average)
                        calculateTime(item.runtime)
                        completeUI()
                    })
            }
            "tv" -> {
                mTV_ViewModel.getTVDetails(args.movieInfo.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        movieClass = MovieViewClass(item)
                        calculateRating(item.vote_average)
                        calculateTime(item.episode_run_time)
                        completeUI()
                    })
            }
            else -> {
                Toast.makeText(requireContext(), "Błąd danych", Toast.LENGTH_LONG).show()
            }
        }

        view.favouriteButton.setOnClickListener {
            if (::movieClass.isInitialized) {
                mFavouriteViewModel.checkExist(movieClass.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        if (item > 0) {
                            //USUWAMY
                            mFavouriteViewModel.deleteFavourite(movieClass.ID)
                            favouriteButton.setImageResource(R.drawable.ic_star)
                            Toast.makeText(
                                requireContext(),
                                "${movieClass.title} został usunięty z listy ulubionych",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            //DODAJEMY
                            mFavouriteViewModel.addFavourite(movieClass.createFavourite())
                            favouriteButton.setImageResource(R.drawable.ic_star_true)
                            Toast.makeText(
                                requireContext(),
                                "${movieClass.title} został dodany do listy ulubionych",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }

        view.containerButton.setOnClickListener {
            buttonContainerClicked = !buttonContainerClicked;
            ChangeButtonsStyle(buttonContainerClicked)
        }
        return view
    }

    companion object {
        fun newInstance() = MovieView()
    }

    private fun completeUI() {
        if (::movieClass.isInitialized) {
            bindImage(backgroundImage, imageSource + movieClass.backgroundPath)
            bindImage(posterImage, imageSource + movieClass.posterPath)
            movieTitle.text = movieClass.title
            movieTagline.text = movieClass.tagline
            yearRelease.text = "(${movieClass.releaseDate.split("-")[0]})"
            dateRelease.text = movieClass.releaseDate
            movieType.text = movieClass.movieType
            movieDescription.text = movieClass.movieDescription
        } else {
            Toast.makeText(requireContext(), "Klasa nie zainicjowana", Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateTime(time: List<Int>) {
        if (time.size == 3)
            movieTime.text = "${time[0]}h ${time[1]}m ${time[2]}s"
        else if (time.size == 2)
            movieTime.text = "${time[0]}m ${time[1]}s"
        else if (time.size == 1)
            movieTime.text = "${time[0]}m"
        else {
            movieTime.text = "Pusto"
        }
    }

    private fun calculateTime(time: Int) {
        var minutes = time % 60
        var hours = (time - (time % 60)) / 60
        movieTime.text = "${hours}h ${minutes}m"
    }

    private fun calculateRating(voteAverate: Double) {
        var rating: Int = (voteAverate * 10).toInt()
        if (rating == 0)
            movieRatingText.text = "NR"
        else
            movieRatingText.text = "${rating}%"
        movieRating.progress = rating
    }

    private fun checkIfIsFavourite(ID: Int) {
        if (::mFavouriteViewModel.isInitialized) {
            mFavouriteViewModel.checkExist(ID)
                .observe(viewLifecycleOwner, Observer { item ->
                    if (item > 0) {
                        favouriteButton.setImageResource(R.drawable.ic_star_true)
                    } else {
                        favouriteButton.setImageResource(R.drawable.ic_star)
                    }
                })
        }
    }

    private fun ChangeButtonsStyle(clicked: Boolean){
        if(clicked){
            //Pokaz
            favouriteButton.visibility = View.VISIBLE
            toWatchButton.visibility = View.VISIBLE

            favouriteButton.startAnimation(showButtonAnimation)
            toWatchButton.startAnimation(showButtonAnimation)

            favouriteButton.isClickable = true
            toWatchButton.isClickable = true
        }
        else{
            //Schowaj
            favouriteButton.visibility = View.INVISIBLE
            toWatchButton.visibility = View.INVISIBLE

            favouriteButton.startAnimation(hideButtonAnimation)
            toWatchButton.startAnimation(hideButtonAnimation)

            favouriteButton.isClickable = false
            toWatchButton.isClickable = false
        }
    }

}