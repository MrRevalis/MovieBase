package com.example.moviebase.DataModels.TVDetailFolder

data class Last_episode_to_air (
    val air_date : String,
    val episode_number : Int,
    val id : Int,
    val name : String,
    val overview : String,
    val production_code : String,
    val season_number : Int,
    val still_path : String,
    val vote_average : Double,
    val vote_count : Int
)