package com.example.moviebase.API

class MovieRepository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTrending(mediaType: String, timeWindow: String) = apiRequest {
        movieAPI.getTrending(mediaType, timeWindow)
    }

    suspend fun getMovieDetails(id: Int) = apiRequest {
        movieAPI.getMovieDetails(id)
    }
}