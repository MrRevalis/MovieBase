package com.example.moviebase.DataModels.PopularMovieTVFolder


class WhatContent(popularMovieResults: List<PopularMovieResults>, popularTVResults: List<PopularTVResults>){
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
    }
}