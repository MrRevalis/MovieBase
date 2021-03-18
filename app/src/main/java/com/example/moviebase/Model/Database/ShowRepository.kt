package com.example.moviebase.Model.Database

import androidx.lifecycle.LiveData

class ShowRepository (private val showDao: ShowDao){
    val getEverything : LiveData<List<Show>> = showDao.getEverything()
    //EGZAMIN zad2
    val getFavourite: LiveData<List<Show>> = showDao.getFavoutire()
    val getToWatch: LiveData<List<Show>> = showDao.getToWatch()

    suspend fun addItem(show: Show){
        showDao.insertFavourite(show)
    }

    fun removeItem(itemID: Int){
        showDao.deleteFavourite(itemID)
    }

    fun checkExist(itemID: Int) : Int{
        return showDao.checkExist(itemID)
    }

    suspend fun checkFavourite(value : Int): Boolean{
        return showDao.checkFavourite(value)
    }

    suspend fun checkToWatch(value : Int): Boolean{
        return showDao.checkToWatch(value)
    }

    suspend fun changeFavourite(movieID : Int, value : Boolean){
        showDao.changeFavourite(movieID, value)
    }

    suspend fun changeToWatch(movieID : Int, value : Boolean){
        showDao.changeToWatch(movieID, value)
    }

}