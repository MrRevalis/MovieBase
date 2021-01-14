package com.example.moviebase.ViewModel

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.SearchModelFolder.*
import com.example.moviebase.R
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(MovieAPI())
    var searchQuery: String = ""

    var movies = MutableLiveData<SearchMovie>()
    var people = MutableLiveData<SearchPeople>()
    var shows = MutableLiveData<SearchTv>()

    val hidden = mutableListOf<SearchResultListModel.ResultType>()

    fun search(){
        if(!searchQuery.isNullOrEmpty()){
            viewModelScope.launch {
                movies.postValue(repository.searchMovies(searchQuery))
                people.postValue(repository.searchPeople(searchQuery))
                shows.postValue(repository.searchTv(searchQuery))
            }
        }
    }

    fun isHidden(group: String): Boolean{
        return when(group){
            "movie" -> hidden.contains(SearchResultListModel.ResultType.MOVIE)
            "people" -> hidden.contains(SearchResultListModel.ResultType.PERSON)
            "show" -> hidden.contains(SearchResultListModel.ResultType.SHOW)
            else -> false
        }
    }

    fun getResultType(group: String): SearchResultListModel.ResultType{
        return when(group){
            "movie" -> SearchResultListModel.ResultType.MOVIE
            "people" -> SearchResultListModel.ResultType.PERSON
            "show" -> SearchResultListModel.ResultType.SHOW
            else -> SearchResultListModel.ResultType.NODEFINED
        }
    }

    fun getGroup(group: String): List<Any>{
        return when(group){
            "movie" -> movies.value?.results!!
            "people" -> people.value?.results!!
            "show" -> shows.value?.results!!
            else -> listOf()
        }
    }
}