package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.API.MovieAPI
import com.example.moviebase.API.MovieRepository
import com.example.moviebase.DataModels.PersonDetailFolder.PersonDetail
import com.example.moviebase.DataModels.PersonMoviesTVsFolder.PersonMovieTVCredits
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularMovieDetail
import com.example.moviebase.DataModels.PopularMovieTVFolder.PopularPersonDetail
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

    fun getPersonMoviesTVs(person_id: Int) : LiveData<PersonMovieTVCredits>{
        var results = MutableLiveData<PersonMovieTVCredits>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getPersonMoviesTVs(person_id)
            results.postValue(arrivedData)
        }
        return results
    }

    //Egzamin
    fun getPopularPerson() : LiveData<PopularPersonDetail>{
        var results = MutableLiveData<PopularPersonDetail>()
        viewModelScope.launch(Dispatchers.IO){
            val arrivedData = repository.getPopularPerson()
            results.postValue(arrivedData)
        }
        return results
    }

}