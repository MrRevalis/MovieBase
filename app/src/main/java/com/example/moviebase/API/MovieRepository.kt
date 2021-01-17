package com.example.moviebase.API

class MovieRepository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTrending(mediaType: String, timeWindow: String) = apiRequest {
        movieAPI.getTrending(mediaType, timeWindow)
    }

    suspend fun getMovieDetails(id: Int) = apiRequest {
        movieAPI.getMovieDetails(id)
    }

    suspend fun searchMovies(query: String, page: Int) = apiRequest {
        movieAPI.searchMovies(query, page)
    }

    suspend fun searchPeople(query: String, page: Int) = apiRequest {
        movieAPI.searchPeople(query, page)
    }

    suspend fun searchTv(query: String, page: Int) = apiRequest {
        movieAPI.searchTv(query, page)
    }
}