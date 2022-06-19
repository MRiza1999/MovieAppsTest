package com.example.movieapps.core.persistences.mapper.main

import com.example.movieapps.core.data.main.source.remote.response.*
import com.example.movieapps.core.domain.main.model.*
import com.example.movieapps.util.Utils

object MainDataMapper {

    fun mapResponseGenreListToEntity(input:List<GenresItem?>):List<GenreListEntity> =
        input.map {
            GenreListEntity(
                name = it?.name,
                id = it?.id
            )
        }

    fun mapResponseMoviePopularToEntity(input: List<ResultsItem?>):List<MoviePopularEntity> =
        input.map {
            MoviePopularEntity(
                title = it?.title,
                posterPath = it?.posterPath,
                backdropPath = it?.backdropPath,
                id = it?.id
            )
        }

    fun mapResponseMovieTopRatedToEntity(input: List<ResultsItem?>):List<MovieTopRatedEntity> =
        input.map {
            MovieTopRatedEntity(
                title = it?.title,
                posterPath = it?.posterPath,
                backdropPath = it?.backdropPath,
                id = it?.id
            )
        }

    fun mapResponseMovieComingSoonToEntity(input: List<ResultsItem?>):List<MovieComingSoonEntity> =
        input.map {
            MovieComingSoonEntity(
                title = it?.title,
                posterPath = it?.posterPath,
                backdropPath = it?.backdropPath,
                id = it?.id
            )
        }

    fun mapResponseMovieReviewToEntity(input:List<ResultsItemReview?>):List<MovieReviewEntity> =
        input.map {
            MovieReviewEntity(
                userName = it?.authorDetails?.username,
                avatarPath = it?.authorDetails?.avatarPath,
                content = it?.content,
                rating = it?.authorDetails?.rating?.toFloat()
            )
        }

    fun mapResponseMovieDetailToEntity(input:ResponseDetailMovie?):MovieDetailEntity =
        MovieDetailEntity(
            id = input?.id,
            title = input?.title,
            posterPath = input?.posterPath,
            backdropPath = input?.backdropPath,
            overView = input?.overview,
            vote = input?.voteAverage.toString(),
            genre = Utils.genreListToString(input!!.genres)
        )

    fun mapResponseMovieTrailerToEntity(input:List<ResultsItemTrailer?>):List<MovieTrailerEntity> =
        input.map {
            MovieTrailerEntity(
                key = it?.key,
                site = it?.site
            )
        }
}