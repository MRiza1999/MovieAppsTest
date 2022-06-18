package com.example.movieapps.core.data.main.source

import com.example.movieapps.core.ApiResponse
import com.example.movieapps.core.data.main.source.remote.MainRemoteDataSource
import com.example.movieapps.core.domain.main.model.GenreListEntity
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
                val response = MainDataMapper.mapResponseToEntity(apiResponse.data.genres)
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