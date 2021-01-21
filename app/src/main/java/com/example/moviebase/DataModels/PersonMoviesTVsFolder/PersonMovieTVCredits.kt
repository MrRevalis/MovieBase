package com.example.moviebase.DataModels.PersonMoviesTVsFolder

data class PersonMovieTVCredits(
    val cast: List<PersonMovieTVCast>,
    val crew: List<PersonMovieTVCrew>,
    val id: Int
)
