package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.CrewShowFolder.CrewShow
import com.example.moviebase.DataModels.MovieDetailFolder.MovieDetail
import com.example.moviebase.DataModels.MovieVideosFolder.MovieVideos
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularMovieDetail
import com.example.moviebase.DataModels.TrendingModelFolder.TrendingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(MovieAPI())

    fun getTrending(mediaType: String, timeWindow: String): LiveData<TrendingModel> {
        var trending = MutableLiveData<TrendingModel>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTrending(mediaType, timeWindow)
            trending.postValue(arrivedData)
        }
        return trending
    }

    fun getMovieDetails(id: Int) : LiveData<MovieDetail>{
        var movie = MutableLiveData<MovieDetail>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getMovieDetails(id)
            movie.postValue(arrivedData)
        }
        return movie
    }

    fun getMovieCrew(movieID: Int) : LiveData<CrewShow>{
        var results = MutableLiveData<CrewShow>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getMovieCrew(movieID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun getMovieVideos(movieID: Int) : LiveData<MovieVideos>{
        var results = MutableLiveData<MovieVideos>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getMovieVideos(movieID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun getPopularMovie() : LiveData<PopularMovieDetail>{
        var results = MutableLiveData<PopularMovieDetail>()
        viewModelScope.launch(Dispatchers.IO){
            val arrivedData = repository.getPopularMovie()
            results.postValue(arrivedData)
        }
        return results
    }
}