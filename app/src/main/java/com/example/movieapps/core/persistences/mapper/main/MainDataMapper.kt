package com.example.movieapps.core.persistences.mapper.main

import com.example.movieapps.core.data.main.source.remote.response.GenresItem
import com.example.movieapps.core.domain.main.model.GenreListEntity

object MainDataMapper {

    fun mapResponseToEntity(input:List<GenresItem?>):List<GenreListEntity> =
        input.map {
            GenreListEntity(
                name = it?.name,
                id = it?.id
            )
        }

}