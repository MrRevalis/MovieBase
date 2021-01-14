package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.API.TV_Repository
import com.example.moviebase.DataModels.TVDetailFolder.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TV_ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TV_Repository = TV_Repository(MovieAPI())

    fun getTVDetails(id: Int): LiveData<TV> {
        var tv = MutableLiveData<TV>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getTVDetails(id)
            tv.postValue(arrivedData)
        }
        return tv
    }
}