package com.example.movieapps.core.data.main.source.remote.network

import com.example.movieapps.core.data.main.source.remote.response.ResponseGenreList
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {


    @GET("3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key")apiKey:String,
    ):ResponseGenreList

}