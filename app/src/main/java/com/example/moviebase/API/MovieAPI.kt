package com.example.moviebase.API

import com.example.moviebase.DataModels.TrendingModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val API: String = "?api_key=fbfd2d53b7504d595ee9c450e52d4026"

interface MovieAPI {
    //API: fbfd2d53b7504d595ee9c450e52d4026
    //Zapytania: https://developers.themoviedb.org/3/getting-started/introduction
    //Przetwarzanie obrazów: https://developers.themoviedb.org/3/getting-started/images
    /*
    Trendujące filmy/seriale/osoby na dzień dzisiejszy lub tydzień
    https://developers.themoviedb.org/3/trending/get-trending
    mediaType => all/movie/tv/person
    timeWindow => day/week
    */
    @GET("trending/{mediaType}/{timeWindow}${API}")
    suspend fun getTrending(@Path("mediaType") mediaType : String, @Path("timeWindow") timeWindow : String) : Response<TrendingModel>

    companion object{
        operator fun invoke() : MovieAPI{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()
                .create(MovieAPI::class.java)
        }
    }
}