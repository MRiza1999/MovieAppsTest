package com.example.movieapps.core.data.main.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseGenreList(

	@field:SerializedName("genres")
	val genres: List<GenresItem?> = emptyList()

)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
