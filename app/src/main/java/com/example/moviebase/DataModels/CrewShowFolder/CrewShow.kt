package com.example.moviebase.DataModels.CrewShowFolder

data class CrewShow (
    val id : Int,
    val cast : List<Cast>,
    val crew : List<Crew>
)