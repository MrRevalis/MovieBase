package com.example.moviebase.DataModels.PopularMovieTVFolder

data class PopularMovieDetail(
    val page: Int,
    val results: List<PopularMovieResults>,
    val total_results: Int,
    val total_pages: Int
)