package com.example.moviebase.DataModels.SearchModelFolder

data class SearchPeople(
    val page: Int,
    val results: List<PeopleSearchResult>,
    val total_results: Int,
    val total_pages: Int
)