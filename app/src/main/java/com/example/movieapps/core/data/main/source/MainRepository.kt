package com.example.movieapps.core.data.main.source

import com.example.movieapps.core.ApiResponse
import com.example.movieapps.core.data.main.source.remote.MainRemoteDataSource
import com.example.movieapps.core.domain.main.model.*
import com.example.movieapps.core.domain.main.repository.IMainRepository
import com.example.movieapps.core.persistences.mapper.main.MainDataMapper
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MainRepository(private val mainRemoteDataSource: MainRemoteDataSource):IMainRepository {

    override fun getGenreList(apiKey: String): Flow<Resource<List<GenreListEntity>>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getGenreList(apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseGenreListToEntity(apiResponse.data.genres)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMoviePopular(apiKey: String): Flow<Resource<List<MoviePopularEntity>>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getMoviePopular(apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseMoviePopularToEntity(apiResponse.data.results)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMovieComingSoon(apiKey: String): Flow<Resource<List<MovieComingSoonEntity>>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getMovieComingSoon(apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseMovieComingSoonToEntity(apiResponse.data.results)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMovieTopRated(apiKey: String): Flow<Resource<List<MovieTopRatedEntity>>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getMovieTopRated(apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseMovieTopRatedToEntity(apiResponse.data.results)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMovieDetail(
        movieId: String,
        apiKey: String
    ): Flow<Resource<MovieDetailEntity>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getMovieDetail(movieId,apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseMovieDetailToEntity(apiResponse.data)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getMovieReview(
        movieId: String,
        apiKey: String
    ): Flow<Resource<List<MovieReviewEntity>>> = flow {
        emit(Resource.Loading())
        when(val apiResponse = mainRemoteDataSource.getMovieReview(movieId,apiKey).first()){
            is ApiResponse.Success->{
                val response = MainDataMapper.mapResponseMovieReviewToEntity(apiResponse.data.results)
                emit(Resource.Success(response))
            }
            is ApiResponse.Empty->{
                emit(Resource.Error("Error"))
            }
            is ApiResponse.Error->{
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }


}