package com.example.movieapps.core.domain.main.usecase

import com.example.movieapps.core.domain.main.model.GenreListEntity
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getGenreList(apiKey:String): Flow<Resource<List<GenreListEntity>>>
}