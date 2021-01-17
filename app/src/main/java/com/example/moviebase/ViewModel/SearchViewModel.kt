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

    private var moviePage = 1
    private var peoplePage = 1
    private var showsPage = 1
    private var searchInProgress = false

    fun search(){
        if(!searchQuery.isNullOrEmpty()){
            viewModelScope.launch {
                movies.postValue(repository.searchMovies(searchQuery, moviePage))
                people.postValue(repository.searchPeople(searchQuery, peoplePage))
                shows.postValue(repository.searchTv(searchQuery, showsPage))
            }
        }
    }

    fun loadNextPages(): LiveData<List<Any>>{
        val result = MutableLiveData<List<Any>>()
        if(!searchInProgress){
            viewModelScope.launch {
                if(!hidden.contains(SearchResultListModel.ResultType.MOVIE)){
                    result.postValue(repository.searchMovies(searchQuery, ++moviePage).results)
                }
                if(!hidden.contains(SearchResultListModel.ResultType.PERSON))
                    result.postValue(repository.searchPeople(searchQuery, ++peoplePage).results)
                if(!hidden.contains(SearchResultListModel.ResultType.SHOW))
                    result.postValue(repository.searchTv(searchQuery, ++showsPage).results)
                searchInProgress = false
            }
        }
        return result
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