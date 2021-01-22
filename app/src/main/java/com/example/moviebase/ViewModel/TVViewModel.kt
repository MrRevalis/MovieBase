package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.TVRepository
import com.example.moviebase.DataModels.CrewShowFolder.CrewShow
import com.example.moviebase.DataModels.MovieVideosFolder.MovieVideos
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularMovieDetail
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularTVDetail
import com.example.moviebase.DataModels.TVDetailFolder.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TVRepository = TVRepository(MovieAPI())

    fun getTVDetails(id: Int): LiveData<TV> {
        var tv = MutableLiveData<TV>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVDetails(id)
            tv.postValue(arrivedData)
        }
        return tv
    }

    fun getTVCrew(id: Int): LiveData<CrewShow> {
        var tv = MutableLiveData<CrewShow>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVCrew(id)
            tv.postValue(arrivedData)
        }
        return tv
    }

    fun getTVVideos(id: Int): LiveData<MovieVideos> {
        var videos = MutableLiveData<MovieVideos>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVVideos(id)
            videos.postValue(arrivedData)
        }
        return videos
    }

    fun getPopularTV() : LiveData<PopularTVDetail>{
        var results = MutableLiveData<PopularTVDetail>()
        viewModelScope.launch(Dispatchers.IO){
            val arrivedData = repository.getPopularTV()
            results.postValue(arrivedData)
        }
        return results
    }
}