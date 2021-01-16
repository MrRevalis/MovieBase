package com.example.moviebase.Model.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val ID: Int,
    val itemID: Int,
    val type: String,
    val title: String,
    val imgPath: String,
    val description: String
)