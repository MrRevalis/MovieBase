package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.PersonDetailFolder.PersonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository(MovieAPI())

    fun getPersonDetails(id: Int) : LiveData<PersonDetail>{
        var person = MutableLiveData<PersonDetail>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getPersonDetails(id)
            person.postValue(arrivedData)
        }
        return person
    }

    //znany z

}