package com.example.moviebase.Model.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Query("DELETE " +
            "FROM favourite_table " +
            "WHERE favourite_table.itemID = :value"
    )
    fun deleteFavourite(value: Int)

    @Query(
        "SELECT COUNT(*) " +
                "FROM favourite_table " +
                "WHERE itemID = :value"
    )
    fun checkExist(value: Int): Int

    @Query(
        "SELECT * " +
                "FROM favourite_table"
    )
    fun getEverything(): LiveData<List<Favourite>>
}