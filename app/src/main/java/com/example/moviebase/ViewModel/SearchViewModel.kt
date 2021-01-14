package com.example.moviebase.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.SearchModelFolder.MovieSearchResult
import com.example.moviebase.DataModels.SearchModelFolder.PeopleSearchResult
import com.example.moviebase.DataModels.SearchModelFolder.SearchResultListModel
import com.example.moviebase.DataModels.SearchModelFolder.TvSearchResult
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(MovieAPI())
    var searchQuery: String = ""

    fun getMovies(): LiveData<List<MovieSearchResult>>{
        val movies = MutableLiveData<List<MovieSearchResult>>()
        if(!searchQuery.isNullOrEmpty())
            viewModelScope.launch {
                movies.postValue(repository.searchMovies(searchQuery).results)
            }
        return movies
    }

    fun getPeople(): LiveData<List<PeopleSearchResult>>{
        val movies = MutableLiveData<List<PeopleSearchResult>>()
        if(!searchQuery.isNullOrEmpty())
            viewModelScope.launch {
                movies.postValue(repository.searchPeople(searchQuery).results)
            }
        return movies
    }

    fun getTvShows(): LiveData<List<TvSearchResult>>{
        val shows = MutableLiveData<List<TvSearchResult>>()
        if(!searchQuery.isNullOrEmpty())
            viewModelScope.launch {
                shows.postValue(repository.searchTv(searchQuery).results)
            }
        return shows
    }
}