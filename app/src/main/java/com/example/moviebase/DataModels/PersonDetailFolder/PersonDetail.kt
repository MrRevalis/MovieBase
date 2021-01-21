package com.example.moviebase.DataModels.PersonDetailFolder

data class PersonDetail(
    val birthday: String,
    val known_for_department: String,
    val deathday: String,
    val id: Int,
    val name: String,
    val also_known_as: List<String>,
    val gender: Int, //0-2
    val biography: String,
    val popularuty: Double,
    val place_of_birth: String,
    val profile_path: String,
    val adult: Boolean,
    val imdb_id: String,
    val homepage: String
)
