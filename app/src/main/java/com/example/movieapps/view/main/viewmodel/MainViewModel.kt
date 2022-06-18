package com.example.movieapps.view.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapps.core.domain.main.model.GenreListEntity
import com.example.movieapps.core.domain.main.usecase.MainUseCase
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val mainUseCase: MainUseCase):ViewModel() {

    private val _isLoadingGenreList = MutableLiveData<Boolean>()
    private val _dataGenreList = MutableLiveData<List<GenreListEntity?>?>()
    private val _isErrorGenreList = MutableLiveData<String>()

    val isLoadingGenreList = _isLoadingGenreList
    val dataGenreList = _dataGenreList
    val isErrorGenreList = _isErrorGenreList

    fun getGenreList(apiKey:String){
        viewModelScope.launch {
            mainUseCase.getGenreList(apiKey)
                .onStart {
                    _isLoadingGenreList.postValue(true)
                }
                .onCompletion {
                    _isLoadingGenreList.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingGenreList.postValue(true)
                        is Resource.Success -> {
                            _isLoadingGenreList.postValue(false)
                            _dataGenreList.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingGenreList.postValue(false)
                            _isErrorGenreList.postValue(data.message!!)
                        }
                    }
                }
        }
    }


}