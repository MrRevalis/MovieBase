package com.example.moviebase.DataModels.PopularMovieTVFolder

//Egzamin
data class PopularPersonDetail(
    val page: Int,
    val results: List<PopularPersonResults>,
    val total_results: Int,
    val total_pages: Int
    )
