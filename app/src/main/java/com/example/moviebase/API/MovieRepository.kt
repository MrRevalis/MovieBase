package com.example.moviebase.API

class MovieRepository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTrending(mediaType: String, timeWindow: String) = apiRequest {
        movieAPI.getTrending(mediaType, timeWindow)
    }

    suspend fun getMovieDetails(id: Int) = apiRequest {
        movieAPI.getMovieDetails(id)
    }

    suspend fun getPersonDetails(id: Int) = apiRequest {
        movieAPI.getPersonDetail(id)
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

    suspend fun getMovieCrew(movieID: Int) = apiRequest {
        movieAPI.getMovieCrew(movieID)
    }

    suspend fun getMovieVideos(movieID: Int) = apiRequest {
        movieAPI.getMovieVideos(movieID)
    }
}