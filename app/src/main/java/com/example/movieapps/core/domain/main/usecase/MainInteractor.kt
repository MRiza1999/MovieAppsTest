package com.example.movieapps.core.domain.main.usecase

import com.example.movieapps.core.domain.main.model.GenreListEntity
import com.example.movieapps.core.domain.main.repository.IMainRepository
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.Flow

class MainInteractor(private val mainRepository: IMainRepository): MainUseCase {

    override fun getGenreList(apiKey: String): Flow<Resource<List<GenreListEntity>>> {
        return mainRepository.getGenreList(apiKey)
    }

}