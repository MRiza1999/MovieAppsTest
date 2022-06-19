package com.example.movieapps.core.domain.main.usecase

import com.example.movieapps.core.domain.main.model.*
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getGenreList(apiKey:String): Flow<Resource<List<GenreListEntity>>>
    fun getMoviePopular(apiKey: String):Flow<Resource<List<MoviePopularEntity>>>
    fun getMovieComingSoon(apiKey: String):Flow<Resource<List<MovieComingSoonEntity>>>
    fun getMovieTopRated(apiKey: String):Flow<Resource<List<MovieTopRatedEntity>>>
    fun getMovieDetail(movieId:String,apiKey: String):Flow<Resource<MovieDetailEntity>>
    fun getMovieReview(movieId:String,apiKey: String):Flow<Resource<List<MovieReviewEntity>>>
}