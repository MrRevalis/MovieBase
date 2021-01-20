package com.example.moviebase.DataModels.SearchModelFolder

data class SearchMovieDetail(
    val adult: Boolean,
    val id: Int,
    val media_type: String,
    val title: String, //movie title
    val name: String //show title
)
