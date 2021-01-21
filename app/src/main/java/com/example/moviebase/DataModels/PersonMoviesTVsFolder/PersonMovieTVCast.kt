package com.example.moviebase.DataModels.PersonMoviesTVsFolder

data class PersonMovieTVCast(
    val id: Int,
    val ooriginal_language: String,
    val episode_count: Int,
    val overview: String,
    val origin_country: List<String>,
    val original_name: String,
    val genre_ids: List<Int>,
    val name: String,
    val media_type: String,
    val poster_path: String,
    val first_air_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val character: String,
    val backdrop_path: String,
    val popularity: Double,
    val credit_id: String,
    val original_title: String,
    val video: Boolean,
    val release_date: String,
    val title: String,
    val adult: Boolean
)