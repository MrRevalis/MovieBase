package com.example.moviebase.DataModels.SearchModelFolder

data class SearchTv(
    val page: Int,
    val results: List<TvSearchResult>,
    val total_results: Int,
    val total_pages: Int
)