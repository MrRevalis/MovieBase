package com.example.moviebase.API

class MovieRepository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTrending(mediaType: String, timeWindow: String) = apiRequest {
        movieAPI.getTrending(mediaType, timeWindow)
    }

    suspend fun getMovieDetails(id: Int) = apiRequest {
        movieAPI.getMovieDetails(id)
    }

    suspend fun searchMovies(query: String) = apiRequest {
        movieAPI.searchMovies(query)
    }

    suspend fun searchPeople(query: String) = apiRequest {
        movieAPI.searchPeople(query)
    }

    suspend fun searchTv(query: String) = apiRequest {
        movieAPI.searchTv(query)
    }
}