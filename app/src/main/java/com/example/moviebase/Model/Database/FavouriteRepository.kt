package com.example.moviebase.Model.Database

import androidx.lifecycle.LiveData

class FavouriteRepository (private val favouriteDao: FavouriteDao){
    val getEverything : LiveData<List<Favourite>> = favouriteDao.getEverything()

    suspend fun addItem(favourite: Favourite){
        favouriteDao.insertFavourite(favourite)
    }

    suspend fun removeItem(itemID: Int){
        favouriteDao.deleteFavourite(itemID)
    }

    suspend fun checkExist(itemID: Int) : Int{
        return favouriteDao.checkExist(itemID)
    }
}