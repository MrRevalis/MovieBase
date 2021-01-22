package com.example.moviebase.DataModels.PopularMovieTVFolder

data class PopularContent(
    val id: Int,
    val type: String,
    val title: String,
    val name: String,
    val poster_path: String,
    val release_date: String,
    val first_air_date: String,
    val popularity: Double,
    val vote_average: Double
)
