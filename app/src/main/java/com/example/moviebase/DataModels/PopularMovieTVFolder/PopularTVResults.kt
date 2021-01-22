package com.example.moviebase.DataModels.PopularMovieTVFolder

data class PopularTVResults(
    val poster_path: String,
    val popularity: Double,
    val id: Int,
    val backdrop_path: String,
    val vote_average: Double,
    val overview: String,
    val first_air_date: String,
    val origin_country: List<String>,
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String
)