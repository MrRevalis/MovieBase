package com.example.moviebase.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.TrendingModel
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
}