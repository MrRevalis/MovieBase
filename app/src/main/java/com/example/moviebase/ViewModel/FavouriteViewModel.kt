package com.example.moviebase.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebase.Model.Database.Favourite
import com.example.moviebase.Model.Database.FavouriteRepository
import com.example.moviebase.Model.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    val getEverything: LiveData<List<Favourite>>
    private val repository: FavouriteRepository

    init {
        val favouriteDao = MainDatabase.getDatabase(
            application
        ).favouriteDao()
        repository = FavouriteRepository(favouriteDao)
        getEverything = repository.getEverything
    }

    fun addFavourite(favourite: Favourite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(favourite)
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
}