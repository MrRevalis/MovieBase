package com.example.moviebase.DataModels.SearchModelFolder

data class PeopleSearchResult (
    val profile_path: String,
    val adult: Boolean,
    val id: Int,
    val name: String,
    val popularity: Double,
    val known_for_department: String
        )
