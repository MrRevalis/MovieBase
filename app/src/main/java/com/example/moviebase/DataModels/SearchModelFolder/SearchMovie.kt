package com.example.moviebase.DataModels.SearchModelFolder

data class SearchMovie(
    val page: Int,
    val results: List<MovieSearchResult>,
    val total_results: Int,
    val total_pages: Int
)