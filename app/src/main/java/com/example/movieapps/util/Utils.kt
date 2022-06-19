package com.example.movieapps.util

import com.example.movieapps.core.data.main.source.remote.response.GenresItem

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

}