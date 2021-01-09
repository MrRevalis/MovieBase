package com.example.moviebase.DataModels.TrendingModelFolder

data class TrendingModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)