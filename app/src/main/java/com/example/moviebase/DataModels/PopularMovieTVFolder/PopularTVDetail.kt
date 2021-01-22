package com.example.moviebase.DataModels.PopularMovieTVFolder

data class PopularTVDetail(
    val page: Int,
    val results: List<PopularTVResults>,
    val total_results: Int,
    val total_pages: Int
)