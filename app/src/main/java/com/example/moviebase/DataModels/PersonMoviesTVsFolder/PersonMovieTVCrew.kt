package com.example.moviebase.DataModels.PersonMoviesTVsFolder

data class PersonMovieTVCrew(
    val id: Int,
    val department: String,
    val original_language: String,
    val episode_count: Int,
    val job: String,
    val overview: String,
    val origin_country: List<String>,
    val original_name: String,
    val vote_count: Int,
    val name: String,
    val media_type: String,
    val popularity: Double,
    val credit_id: String,
    val backdrop_path: String,
    val first_air_date: String,
    val vote_average: Double,
    val genre_ids: List<Int>,
    val poster_path: String,
    val original_title: String,
    val video: Boolean,
    val title: String,
    val adult: Boolean,
    val release_date: String
    )