package com.example.movieapps.core.data.main.source.remote.network

import com.example.movieapps.core.data.main.source.remote.response.ResponseDetailMovie
import com.example.movieapps.core.data.main.source.remote.response.ResponseGenreList
import com.example.movieapps.core.data.main.source.remote.response.ResponseListMovie
import com.example.movieapps.core.data.main.source.remote.response.ResponseReviewMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {


    @GET("3/genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key")apiKey:String,
    ):ResponseGenreList

    @GET("3/movie/popular")
    suspend fun getMoviePopular(
        @Query("api_key")apiKey:String,
    ): ResponseListMovie

    @GET("3/movie/upcoming")
    suspend fun getMovieComingSoon(
        @Query("api_key")apiKey:String,
    ): ResponseListMovie

    @GET("3/movie/top_rated")
    suspend fun getMovieTopRated(
        @Query("api_key")apiKey:String,
    ): ResponseListMovie

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId")movieId:String,
        @Query("api_key")apiKey:String,
    ): ResponseDetailMovie

    @GET("3/movie/{movieId}/reviews")
    suspend fun getMovieReview(
        @Path("movieId")movieId:String,
        @Query("api_key")apiKey:String,
    ): ResponseReviewMovie

}