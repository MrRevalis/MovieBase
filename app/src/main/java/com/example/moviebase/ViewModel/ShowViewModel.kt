package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.moviebase.DataModels.CrewShowFolder.CrewShow
import com.example.moviebase.Model.Database.Show
import com.example.moviebase.Model.Database.ShowRepository
import com.example.moviebase.Model.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowViewModel(application: Application) : AndroidViewModel(application) {
    val getEverything: LiveData<List<Show>>
    private lateinit var repository: ShowRepository
    //EGZAMIN zad2
    val showArg = MutableLiveData<String>()
    val fullList: LiveData<List<Show>> = Transformations.switchMap(showArg) {
        when(it){
            "favourite" -> repository.getFavourite
            "toWatch" -> repository.getToWatch
            else -> repository.getEverything
        }
    }

    init {
        val showDao = MainDatabase.getDatabase(
            application
        ).showDao()
        repository = ShowRepository(showDao)
        getEverything = repository.getEverything
    }

    fun addFavourite(show: Show) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(show)
        }
    }

    fun deleteFavourite(itemID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeItem(itemID)
        }
    }

    fun checkExist(itemID: Int): LiveData<Int> {
        var results = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.checkExist(itemID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun checkFavourite(itemID: Int) : LiveData<Boolean>{
        var results = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.checkFavourite(itemID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun checkToWatch(itemID: Int) : LiveData<Boolean>{
        var results = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.checkToWatch(itemID)
            results.postValue(arrivedData)
        }
        return results
    }

    fun changeFavourite(movieID : Int, value : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeFavourite(movieID, value)
        }
    }

    fun changeToWatch(movieID : Int, value : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeToWatch(movieID, value)
        }
    }
}