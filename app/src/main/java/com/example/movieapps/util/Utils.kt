package com.example.movieapps.util

import com.example.movieapps.core.data.main.source.remote.response.GenresItem
import com.example.movieapps.core.domain.main.model.GenreListEntity

object Utils {

    fun genreListToString(listGenre:List<GenresItem?>):String{
        var result = ""
        for (item in listGenre){
            if (result==""){
                result = "${item?.name}"
            }else{
                result = "$result,${item?.name}"
            }
        }
        return result
    }
    fun genreListToString(listGenre:ArrayList<GenreListEntity?>):String{
        var result = ""
        for (item in listGenre){
            if (result==""){
                result = "${item?.id}"
            }else{
                result = "$result,${item?.id}"
            }
        }
        return result
    }

}