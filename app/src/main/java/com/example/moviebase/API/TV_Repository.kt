package com.example.moviebase.API

class TV_Repository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTVDetails(id: Int) = apiRequest {
        movieAPI.getTVDetails(id)
    }
}