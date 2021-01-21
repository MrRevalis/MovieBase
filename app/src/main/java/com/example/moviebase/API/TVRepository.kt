package com.example.moviebase.API

import com.example.moviebase.DataModels.CrewShowFolder.CrewShow

class TVRepository(private val movieAPI: MovieAPI) : SafeApiRequest() {
    suspend fun getTVDetails(id: Int) = apiRequest {
        movieAPI.getTVDetails(id)
    }

    suspend fun getTVCrew(tvID: Int) = apiRequest{
        movieAPI.getTVCrew(tvID)
    }

    suspend fun getTVVideos(tvID: Int) = apiRequest{
        movieAPI.getTVVideos(tvID)
    }

}