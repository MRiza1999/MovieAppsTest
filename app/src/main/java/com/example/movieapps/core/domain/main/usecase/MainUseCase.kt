package com.example.movieapps.core.domain.main.usecase

import androidx.paging.PagingData
import com.example.movieapps.core.data.main.source.remote.response.ResultsItem
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
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
    fun getMovieReviewPaging(movieId:String,apiKey: String):Flow<PagingData<ResultsItemReview>>
    fun getMovieListPaging(apiKey:String,genre:String):Flow<PagingData<ResultsItem>>
}