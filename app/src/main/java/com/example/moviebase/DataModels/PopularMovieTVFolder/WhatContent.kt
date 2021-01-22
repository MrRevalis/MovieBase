package com.example.moviebase.DataModels.PopularMovieTVFolder


class WhatContent(popularMovieResults: List<PopularMovieResults>, popularTVResults: List<PopularTVResults>, popularPersonResults: List<PopularPersonResults>){
    val results = mutableListOf<PopularContent>()

    init {
        for (item in popularMovieResults){
            results.add(
                PopularContent(
                    item.id,
                    "movie",
                    item.title,
                    "",
                    item.poster_path,
                    item.release_date,
                    "",
                    item.popularity,
                    item.vote_average
                )
            )
        }
        for (item in popularTVResults){
            results.add(
                PopularContent(
                    item.id,
                    "tv",
                    "",
                    item.name,
                    item.poster_path,
                    "",
                    item.first_air_date,
                    item.popularity,
                    item.vote_average
                )
            )
        }
        //Egzamin
        for (item in popularPersonResults){
            results.add(
                PopularContent(
                    item.id,
                    "person",
                    "",
                    item.name,
                    item.profile_path,
                    "",
                    "",
                    item.popularity,
                    0.0//item.known_for.vote_average
                )
            )
        }
    }
}