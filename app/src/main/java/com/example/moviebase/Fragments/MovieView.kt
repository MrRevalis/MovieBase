package com.example.moviebase.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.moviebase.Adapter.bindImage
import com.example.moviebase.DataModels.MovieViewClass
import com.example.moviebase.R
import com.example.moviebase.ViewModel.ShowViewModel
import com.example.moviebase.ViewModel.MovieViewModel
import com.example.moviebase.ViewModel.TV_ViewModel
import kotlinx.android.synthetic.main.fragment_movie_view.*
import kotlinx.android.synthetic.main.fragment_movie_view.view.*

const val imageSource: String = "https://image.tmdb.org/t/p/w500"

class MovieView : Fragment() {

    private val args by navArgs<MovieViewArgs>()
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mTV_ViewModel: TV_ViewModel
    private lateinit var mShowViewModel: ShowViewModel
    private lateinit var movieClass: MovieViewClass
    var buttonContainerClicked : Boolean  = false;
    private var isFavourite = false
    private var isToWatch = false
    private var showExist = false

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
        mShowViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)

        showButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.show_animation)
        hideButtonAnimation = AnimationUtils.loadAnimation(context, R.anim.hide_animation)

        checkSetting(args.movieInfo.ID)
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
                mShowViewModel.checkExist(movieClass.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        if(item > 0){
                            if (isFavourite) {
                                //USUWAMY
                                if(!!isFavourite && !isToWatch){
                                    mShowViewModel.deleteFavourite(movieClass.ID)
                                }
                                else{
                                    mShowViewModel.changeFavourite(movieClass.ID, false)
                                }
                                isFavourite = false
                                favouriteButton.setImageResource(R.drawable.ic_star)
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} został usunięty z listy ulubionych",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                //DODAJEMY
                                mShowViewModel.changeFavourite(movieClass.ID, true)
                                favouriteButton.setImageResource(R.drawable.ic_star_true)
                                isFavourite = true
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} został dodany do listy ulubionych",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        else{
                            //NIEISTNIEJE
                            mShowViewModel.addFavourite(movieClass.createFavourite(!isFavourite, isToWatch))
                            favouriteButton.setImageResource(R.drawable.ic_star_true)
                            isFavourite = true
                            Toast.makeText(
                                requireContext(),
                                "${movieClass.title} został dodany do listy ulubionych",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
            }
        }


        view.toWatchButton.setOnClickListener {
            if (::movieClass.isInitialized) {
                mShowViewModel.checkExist(movieClass.ID)
                    .observe(viewLifecycleOwner, Observer { item ->
                        if(item > 0){
                            if (isToWatch) {
                                //USUWAMY
                                if(!!isToWatch && !isFavourite){
                                    mShowViewModel.deleteFavourite(movieClass.ID)
                                }
                                else{
                                    mShowViewModel.changeToWatch(movieClass.ID, false)
                                }
                                isToWatch = false
                                toWatchButton.setImageResource(R.drawable.ic_eye)
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} został usunięty z listy ulubionych",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                //DODAJEMY
                                mShowViewModel.changeToWatch(movieClass.ID, true)
                                toWatchButton.setImageResource(R.drawable.ic_eye_true)
                                isToWatch = true
                                Toast.makeText(
                                    requireContext(),
                                    "${movieClass.title} został dodany do listy ulubionych",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        else{
                            //NIEISTNIEJE
                            mShowViewModel.addFavourite(movieClass.createFavourite(isFavourite, !isToWatch))
                            toWatchButton.setImageResource(R.drawable.ic_eye_true)
                            isToWatch = true
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

    private fun checkSetting(ID: Int) {
        if (::mShowViewModel.isInitialized) {
            mShowViewModel.checkExist(ID)
                .observe(viewLifecycleOwner, Observer { item ->
                    if (item > 0) {
                        //ISTNIEJE
                        showExist = true
                        mShowViewModel.checkFavourite(ID).observe(viewLifecycleOwner, Observer { value->
                            Log.d("komunikat", value.toString())
                            if(value){
                                isFavourite = true
                                favouriteButton.setImageResource(R.drawable.ic_star_true)
                            }
                            else
                                favouriteButton.setImageResource(R.drawable.ic_star)
                        })

                        mShowViewModel.checkToWatch(ID).observe(viewLifecycleOwner, Observer { value->
                            if(value){
                                isToWatch = true
                                toWatchButton.setImageResource(R.drawable.ic_eye_true)
                            }
                            else
                                toWatchButton.setImageResource(R.drawable.ic_eye)
                        })

                    } else {
                        //NIEISTNIEJE
                        showExist = false
                        favouriteButton.setImageResource(R.drawable.ic_star)
                        toWatchButton.setImageResource(R.drawable.ic_eye)
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