package com.example.moviebase.DataModels

import com.example.moviebase.DataModels.Result

data class TrendingModel(
        val page: Int,
        val results: List<Result>,
        val total_pages: Int,
        val total_results: Int
)