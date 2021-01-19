package com.example.moviebase.Model.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShowDao {
    @Insert
    suspend fun insertFavourite(show: Show)

    @Query(
        "DELETE " +
                "FROM show_table " +
                "WHERE show_table.itemID = :value"
    )
    fun deleteFavourite(value: Int)

    @Query(
        "SELECT COUNT(*) " +
                "FROM show_table " +
                "WHERE itemID = :value"
    )
    fun checkExist(value: Int): Int

    @Query(
        "SELECT * " +
                "FROM show_table"
    )
    fun getEverything(): LiveData<List<Show>>

    @Query(
        "SELECT favourite " +
                "FROM show_table " +
                "WHERE show_table.itemId = :value"
    )
    fun checkFavourite(value: Int): Boolean

    @Query(
        "SELECT toWatch " +
                "FROM show_table " +
                "WHERE show_table.itemId = :value"
    )
    fun checkToWatch(value: Int): Boolean

    @Query(
        "UPDATE show_table " +
                "SET favourite = :value " +
                "WHERE show_table.itemId = :movieID"
    )
    fun changeFavourite(movieID: Int, value: Boolean)

    @Query(
        "UPDATE show_table " +
                "SET toWatch = :value " +
                "WHERE show_table.itemId = :movieID"
    )
    fun changeToWatch(movieID: Int, value: Boolean)

    @Query(
        "SELECT * " +
                "FROM show_table " +
                "WHERE show_table.itemId = :movieID " +
                "LIMIT 1"
    )
    fun getShow(movieID: Int) : LiveData<Show>

}