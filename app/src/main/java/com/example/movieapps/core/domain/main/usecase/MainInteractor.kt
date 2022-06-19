package com.example.movieapps.core.domain.main.usecase

import androidx.paging.PagingData
import com.example.movieapps.core.data.main.source.remote.response.ResultsItem
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
import com.example.movieapps.core.domain.main.model.*
import com.example.movieapps.core.domain.main.repository.IMainRepository
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.Flow

class MainInteractor(private val mainRepository: IMainRepository): MainUseCase {

    override fun getGenreList(apiKey: String): Flow<Resource<List<GenreListEntity>>> {
        return mainRepository.getGenreList(apiKey)
    }

    override fun getMoviePopular(apiKey: String): Flow<Resource<List<MoviePopularEntity>>> {
        return mainRepository.getMoviePopular(apiKey)
    }

    override fun getMovieComingSoon(apiKey: String): Flow<Resource<List<MovieComingSoonEntity>>> {
        return mainRepository.getMovieComingSoon(apiKey)
    }

    override fun getMovieTopRated(apiKey: String): Flow<Resource<List<MovieTopRatedEntity>>> {
        return mainRepository.getMovieTopRated(apiKey)
    }

    override fun getMovieDetail(
        movieId: String,
        apiKey: String
    ): Flow<Resource<MovieDetailEntity>> {
        return mainRepository.getMovieDetail(movieId, apiKey)
    }

    override fun getMovieReview(
        movieId: String,
        apiKey: String
    ): Flow<Resource<List<MovieReviewEntity>>> {
        return mainRepository.getMovieReview(movieId,apiKey)
    }

    override fun getMovieReviewPaging(
        movieId: String,
        apiKey: String
    ): Flow<PagingData<ResultsItemReview>> {
        return mainRepository.getMovieReviewPaging(movieId, apiKey)
    }

    override fun getMovieListPaging(apiKey: String, genre: String): Flow<PagingData<ResultsItem>> {
        return mainRepository.getMovieListPaging(apiKey, genre)
    }

}