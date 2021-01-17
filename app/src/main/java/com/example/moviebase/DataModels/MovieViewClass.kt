package com.example.moviebase.DataModels

import com.example.moviebase.DataModels.MovieDetailFolder.Genres
import com.example.moviebase.DataModels.MovieDetailFolder.MovieDetail
import com.example.moviebase.DataModels.TVDetailFolder.TV
import com.example.moviebase.Model.Database.Favourite

public class MovieViewClass {
    val ID: Int
    val type: String
    val posterPath: String
    val backgroundPath: String
    val title: String
    val tagline: String
    val releaseDate: String
    val movieType: String
    val movieDescription: String

    public constructor(movieData: MovieDetail){
        this.ID = movieData.id
        this.type = "movie"
        this.posterPath = movieData.poster_path
        this.backgroundPath = movieData.backdrop_path
        this.title = movieData.title
        this.tagline = movieData.tagline
        this.releaseDate = movieData.release_date
        this.movieType = createMovieType(movieData.genres)
        this.movieDescription = movieData.overview
    }

    public constructor(tvData: TV){
        this.ID = tvData.id
        this.type = "tv"
        this.posterPath = tvData.poster_path
        this.backgroundPath = tvData.backdrop_path
        this.title = tvData.name
        this.tagline = tvData.tagline
        this.releaseDate = tvData.first_air_date
        this.movieType = createMovieType(tvData.genres)
        this.movieDescription = tvData.overview
    }

    private fun createMovieType(genres: List<Genres>) : String{
        var types: StringBuilder = StringBuilder()
        for (x in genres) {
            types.append("${x.name} ")
        }
        return types.toString()
    }

    fun createFavourite() : Favourite{
        return Favourite(0, this.ID, this.type, this.posterPath, this.backgroundPath, this.title, this.tagline, this.releaseDate, this.movieType, this.movieDescription)
    }
}